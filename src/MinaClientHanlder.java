import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * Created by magichuang on 17-3-8.
 */
public class MinaClientHanlder implements IoHandler {
    private MinaClient client;

    public void setClient(MinaClient client) {
        this.client = client;
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

    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        if (o.equals("pso")) {
            ioSession.write(client.getNodeMessage());
            System.out.println("主节点发出请求");
        }

        if (o instanceof byte[]) {
            byte[] buffer = (byte[]) o;
            File file = new File("Task.class");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
        }

    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {


    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {
        // System.out.println("Client inputClosed");
    }
}
