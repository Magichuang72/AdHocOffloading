import org.apache.mina.core.session.IoSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by magichuang on 17-3-9.
 */
public class Pso {
    HashMap<IoSession, Task> mapTask;
    HashMap<IoSession, NodeMessage> mapClientData;
    Map<Long, IoSession> mapSession;
    LinkedList<Loadable> taskLinkedList;
    LinkedList<IoSession> sessionsList;
    CountDownLatch countDownlatch;
    MinaServerHanlder hanlder;
    int[] wantTask;
    int[] speed;
    int[] cost;
    int[] task;

    static {
        System.out.println(System.getProperties().get("java.library.path"));
        System.loadLibrary("jni");
    }

    Pso(Map x, MinaServerHanlder hanlder) {
        mapTask = new HashMap<>();
        mapSession = x;
        this.hanlder = hanlder;
        taskLinkedList = new LinkedList<>();
        taskLinkedList.add(new Task(4));
    }

    private native int[] algorithmStart(int[] a, int[] b, int[] c, int[] d);

    public void start() throws IOException {
        countDownlatch = new CountDownLatch(mapSession.size());
        broadcastRequest();

        try {
            countDownlatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        preProcessTask();
        preProcessNode();
        psoStart();


    }

    public CountDownLatch getCountDownlatch() {
        return countDownlatch;
    }

    private void broadcastRequest() {
        Set<Map.Entry<Long, IoSession>> set = mapSession.entrySet();
        for (Map.Entry<Long, IoSession> temp : set) {
            IoSession iosession = temp.getValue();
            iosession.write("pso");
        }
    }


    private void preProcessTask() {
        int length = taskLinkedList.size();

        task = new int[length];
        if (length == 0) {
            System.out.println("No Task to process");
            return;
        }
        for (int i = 0; i < length; i++) {
            task[i] = taskLinkedList.get(i).getLength();
        }

    }

    private void preProcessNode() {
        sessionsList = new LinkedList<>();
        mapClientData = hanlder.getMapData();
        int length = mapClientData.size();
        if (length == 0) {
            System.out.println("No Node to offload");
            return;
        }
        cost = new int[length];
        speed = new int[length];
        wantTask = new int[length];
        int i = 0;
        for (Map.Entry<IoSession, NodeMessage> entry : mapClientData.entrySet()) {
            IoSession session = entry.getKey();
            NodeMessage nodeMessage = entry.getValue();
            sessionsList.add(session);
            cost[i] = nodeMessage.getCost();
            speed[i] = nodeMessage.getSpeed();
            wantTask[i] = nodeMessage.getWantNode();
            i++;
        }

    }

    private void psoStart() {
        System.out.println("PSO Start");
        if (task == null || speed == null || cost == null || wantTask == null) {
            System.out.println("数据不够无法计算");
            return;
        }
        System.out.println("调用链接库");
        int[] result = algorithmStart(task, speed, cost, wantTask);
        System.out.println("PSO end");
        System.out.println(result[2]);
        System.out.println("开始根据结果分配" + taskLinkedList.size() + "个任务");
        for (int i = 0; i < taskLinkedList.size(); i++) {
            IoSession session = sessionsList.get(result[i]);
            try {
                sendClass(session, taskLinkedList.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            session.write(taskLinkedList.get(i));
        }
    }


    public void sendClass(IoSession session, Object task) throws IOException {
        String Path = task.getClass().getResource("").getPath();
        File fileClass = new File(Path + "/Task.class");
        FileInputStream fis = new FileInputStream(fileClass);
        byte[] buffer = new byte[2 * 1024];
        int size = fis.read(buffer);
        byte[] result = new byte[size];
        System.arraycopy(buffer, 0, result, 0, size);
        // System.out.println("------------class-------"+Thread.currentThread()+"------------------");

        session.write(result);
        // System.out.println("-------------class------"+Thread.currentThread()+"------------------");
        System.out.println("server send class to" + session.getId());

    }


}
