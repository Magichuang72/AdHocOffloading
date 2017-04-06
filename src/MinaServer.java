import org.apache.mina.core.filterchain.DefaultIoFilterChain;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DefaultDatagramSessionConfig;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by magichuang on 17-3-8.
 */
public class MinaServer {
    private int port;
    private SocketAcceptor socketAcceptor;
    private MinaServerHanlder hanlder;
    private Pso pso;


    public MinaServer(int port) {
        this.port = port;
    }

    public Pso getPso() {
        return pso;
    }

    public void startServer() {
        socketAcceptor = new NioSocketAcceptor();
        hanlder = new MinaServerHanlder();
        pso = new Pso(socketAcceptor.getManagedSessions(), hanlder);
        hanlder.setPso(pso);
        LoggingFilter loggingFilter = new LoggingFilter();
        DefaultIoFilterChainBuilder chain = socketAcceptor.getFilterChain();
        ProtocolCodecFilter filter = new ProtocolCodecFilter(new ObjectSerializationCodecFactory());
        chain.addLast("logger", loggingFilter);
        chain.addLast("objectFilter", filter);
        System.out.println("-------------------"+Thread.currentThread()+"------------------");
        socketAcceptor.setHandler(hanlder);
        try {
            socketAcceptor.bind(new InetSocketAddress(port));
            System.out.println("服务器已运行");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException {
        MinaServer server = new MinaServer(10002);
        server.startServer();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入指令");
        while (true) {
            String cmd = scanner.next();
            if (cmd.equals("a")) {
                System.out.println(Thread.currentThread() + "aaaaaaaaaa");
                server.getPso().start();
            }

        }
    }
}
