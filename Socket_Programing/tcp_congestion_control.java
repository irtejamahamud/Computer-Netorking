package Socket_Programing;

import java.util.Random;
import java.util.Scanner;

public class tcp_congestion_control {

    private int ssthresh;
    private int cwnd;
    private int rtt;
    private boolean congestion;

    public tcp_congestion_control(int in_ssthresh) {
        ssthresh = in_ssthresh;
        cwnd = 1;
        rtt = 0;
        congestion = false;
    }

    public void run() {
        System.out.println("Connected to the Server... ...");
        System.out.println("Enter the number of segment: ");
        Scanner in = new Scanner(System.in);
        int seg_len = in.nextInt();
        int seqNum = 0;

        while (seg_len > seqNum) {

            this.rtt++;
            System.out.println();
            System.out.println("RTT Value: " + this.rtt);
            System.out.println("−−−−−−−−−−−−−−−−−−−−−−−−−−−−");
            System.out.println("Previous Congestion Window Size: " + cwnd);
            System.out.println("Updated Threshold Value: " + ssthresh);
            if (!congestion) {
                if (cwnd < ssthresh) {
                    cwnd = cwnd * 2;
                    System.out.println("---Slow Start Phase Runing---");
                } else if (cwnd >= ssthresh) {
                    cwnd++;
                    System.out.println("---Congestion Avoidence Phase Runing---");
                }
            }
            System.out.println("Updated Window Size: " + cwnd);

            sendpacket(seqNum);
            seqNum += cwnd;

        }
    }

    public void sendpacket(int seqNum) {
        if (!rcvACK()) {
            congestion = true;
            System.out.println("Congestion has been detected");
            if (timeout()) {
                handleTimeoutCongestion();
            } else {
                handle3dupACK();
            }

        } else {
            congestion = false;
        }
    }

    public void handleTimeoutCongestion() {

        System.out.println("Loss Detected by TimeOut");
        ssthresh = cwnd / 2;

        if (ssthresh == 0) {
            ssthresh = 1;
        }
        cwnd = 1;
        retransmit();
    }

    public void handle3dupACK() {
        System.out.println("Loss Detected by 3 Duplicate ACK");
        ssthresh = cwnd / 2;
        if (ssthresh == 0) {
            ssthresh = 1;
        }
        cwnd = ssthresh + 3;
        retransmit();

    }

    public void retransmit() {
        congestion = false;
        System.out.println("Retransmiting");
    }

    public boolean timeout() {
        Random r = new Random();
        return r.nextBoolean();
    }

    public boolean rcvACK() {
        Random r = new Random();
        return r.nextBoolean();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the initial ssthresh value: ");
        int ssthresh = in.nextInt();

        tcp_congestion_control reno = new tcp_congestion_control(ssthresh);
        reno.run();
    }
}