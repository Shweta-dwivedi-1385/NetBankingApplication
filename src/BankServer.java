import java.io.*;
import java.net.*;
import java.sql.*;

public class BankServer {

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(5000)) {
            System.out.println("Bank Server Started on Port 5000...");
            while (true) {
                Socket s = ss.accept();
                new Thread(() -> handleClient(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket s) {

        try (DataInputStream dis = new DataInputStream(s.getInputStream());
             DataOutputStream dos = new DataOutputStream(s.getOutputStream())) {

            BankService bank = new BankService();

            while (true) {

                String cmd = dis.readUTF(); 

                if (cmd.equalsIgnoreCase("exit"))
                    break;

                int acc = dis.readInt();
                String name = dis.readUTF();
                double amount = dis.readDouble(); 

                // Validate Account Number + Name
                boolean valid = bank.validateAccountHolder(acc, name);
                if (!valid) {
                    dos.writeUTF("Account Number & Name Do NOT Match!");
                    dos.writeDouble(-1);  // invalid balance
                    dos.flush();
                    continue;
                }

                switch (cmd) {

                    case "balance":
                        double bal = bank.checkBalance(acc);
                        dos.writeUTF("OK");
                        dos.writeDouble(bal);
                        dos.flush();
                        break;

                    case "deposit":
                        bank.deposit(acc, amount);
                        double newBal1 = bank.checkBalance(acc);
                        dos.writeUTF("Deposit Successful");
                        dos.writeDouble(newBal1);
                        dos.flush();
                        break;

                    case "withdraw":
                        boolean ok = bank.withdraw(acc, amount);
                        double newBal2 = bank.checkBalance(acc);
                        dos.writeUTF(ok ? "Withdraw Successful" : "Insufficient Balance");
                        dos.writeDouble(newBal2);
                        dos.flush();
                        break;

                    default:
                        dos.writeUTF("Invalid Operation");
                        dos.writeDouble(-1);
                        dos.flush();
                }
            }

        } catch (Exception e) {
            System.out.println("Client Disconnected.");
        }
    }
}