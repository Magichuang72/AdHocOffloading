import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by magichuang on 17-3-8.
 */
public class MinaServerHanlder implements IoHandler {
    HashMap<IoSession, NodeMessage> mapData;
    Pso pso;


    MinaServerHanlder() {
        mapData = new HashMap<>();
    }

    public void setPso(Pso pso) {
        this.pso = pso;
    }

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {


    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {


    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {


    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {


    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        System.out.println("exceptionCaught");
    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {

        if (o instanceof NodeMessage) {
            System.out.println("得到一个回应");
            NodeMessage buff = (NodeMessage) o;
            mapData.put(ioSession, buff);
            pso.getCountDownlatch().countDown();

        }

    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
        System.out.println(Thread.currentThread() + "   ----for----" + o);

    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {
        //   System.out.println("inputClosed");

    }

    public HashMap<IoSession, NodeMessage> getMapData() {
        return mapData;
    }
}
