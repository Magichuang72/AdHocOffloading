import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * Created by magichuang on 17-3-8.
 */
public class MinaClient {
    private NioSocketConnector connector;
    private int port;
    private String ip;
    private NodeMessage nodeMessage;

    MinaClient(String ip, int port) {
        this.port = port;
        this.ip = ip;
        nodeMessage = new NodeMessage(100, 100, 100);
    }

    public NodeMessage getNodeMessage() {
        return nodeMessage;
    }

    public NioSocketConnector getConnector() {
        return connector;
    }

    public void startClient() {
        LoggingFilter loggingFilter = new LoggingFilter();
        connector = new NioSocketConnector();
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        ProtocolCodecFilter filter = new ProtocolCodecFilter(new ObjectSerializationCodecFactory());
        chain.addLast("logger", loggingFilter);
        chain.addLast("objectFilter", filter);
        MinaClientHanlder hanlder = new MinaClientHanlder();
        hanlder.setClient(this);
        connector.setHandler(hanlder);
        connector.connect(new InetSocketAddress(ip, port));
//        connector.setConnectTimeoutCheckInterval(30);
//        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", port));
//        cf.awaitUninterruptibly();
//        cf.getSession().getCloseFuture().awaitUninterruptibly();
//        connector.dispose();
    }

    public static void main(String[] args) {
        MinaClient minaClient = new MinaClient("0.0.0.0", 10002);
        minaClient.startClient();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入指令");
        while (true) {
            String cmd = scanner.next();
            if (cmd.equals("a")) {

                System.out.println(minaClient.getConnector().getManagedSessionCount());

            }

        }
    }
}