/**
 * Created by magichuang on 17-3-9.
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            MinaClient minaClient = new MinaClient("0.0.0.0", 10000);

            minaClient.startClient();
        }
    }
}
