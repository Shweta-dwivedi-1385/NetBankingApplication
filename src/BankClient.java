import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class BankClient extends JFrame implements ActionListener {

    private JTextField accField, nameField, amtField;
    private JButton balBtn, depBtn, witBtn, clrBtn;
    private JTextArea area;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public BankClient() {

        try {
            socket = new Socket("localhost", 5000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Start BankServer first!");
            System.exit(0);
        }

        setTitle("SmartBank - Net Banking App");
        setSize(750, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel head = new JLabel("SMARTBANK - DIGITAL BANKING", JLabel.CENTER);
        head.setBounds(0, 10, 750, 40);
        head.setOpaque(true);
        head.setBackground(new Color(0, 60, 120));
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(head);

        Font f = new Font("Segoe UI", Font.BOLD, 14);
		
		Color c = new Color(0, 60, 120);

		
        JLabel l1 = new JLabel("Account No:");
        l1.setBounds(100, 80, 150, 25);
        l1.setFont(f);
		l1.setForeground(c);
        add(l1);

        accField = new JTextField();
        accField.setBounds(260, 80, 250, 25);
        add(accField);

        JLabel l2 = new JLabel("Account Holder Name:");
        l2.setBounds(100, 120, 200, 25);
        l2.setFont(f);
		l2.setForeground(c);
        add(l2);

        nameField = new JTextField();
        nameField.setBounds(260, 120, 250, 25);
        add(nameField);

        JLabel l3 = new JLabel("Amount (Rs):");
        l3.setBounds(100, 160, 150, 25);
        l3.setFont(f);
		l3.setForeground(c);
        add(l3);

        amtField = new JTextField();
        amtField.setBounds(260, 160, 250, 25);
        add(amtField);

        // Buttons — ACTION COMMANDS FIXED HERE
        depBtn = makeButton("Deposit", 90, 210);
        depBtn.setActionCommand("deposit");

        witBtn = makeButton("Withdraw", 260, 210);
        witBtn.setActionCommand("withdraw");

        balBtn = makeButton("Check Balance", 430, 210);
        balBtn.setActionCommand("balance");

        add(depBtn);
        add(witBtn);
        add(balBtn);

        // Output Area
        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane sp = new JScrollPane(area);
        sp.setBounds(100, 270, 540, 200);
		sp.setBorder(BorderFactory.createTitledBorder("Transaction Details"));
		add(sp);
		
        // Reset Button
        clrBtn = new JButton("Reset");
        clrBtn.setBounds(560, 490, 120, 30);
		clrBtn.setBackground(new Color(0, 60, 120)); 
		clrBtn.setForeground(Color.WHITE);
        clrBtn.addActionListener(this);
        add(clrBtn);

        setVisible(true);
    }

    private JButton makeButton(String text, int x, int y) {
        JButton b = new JButton(text);
        b.setBounds(x, y, 150, 35);
        b.setBackground(new Color(0, 102, 204));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.addActionListener(this);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        if (cmd.equals("Reset")) {
            accField.setText("");
            nameField.setText("");
            amtField.setText("");
            area.setText("");
            return;
        }

        new Thread(() -> process(cmd)).start();
    }

    private void process(String cmd) {
        try {
            int acc = Integer.parseInt(accField.getText().trim());
            String name = nameField.getText().trim();
            double amount = amtField.getText().isEmpty() ? 0 :
                            Double.parseDouble(amtField.getText());

            // SEND EXACT PROTOCOL
            synchronized (dos) {
                dos.writeUTF(cmd);  // balance / deposit / withdraw
                dos.writeInt(acc);
                dos.writeUTF(name);
                dos.writeDouble(amount); // always send amount
                dos.flush();
            }

            String msg = dis.readUTF();
            double bal = dis.readDouble();

            if (bal == -1) {  // name mismatch
                setArea(msg);
                return;
            }

            if (cmd.equals("balance")) {
                setArea("Account Summary\n-----------------------\n" +
                        "Name: " + name + "\n" +
                        "Account No: " + acc + "\n" +
                        "Current Balance: Rs " + bal);
            }
            else if (cmd.equals("deposit")) {
                setArea("Deposit Successful\n-----------------------\n" +
                        "Name: " + name + "\n" +
                        "Deposited: Rs " + amount + "\n" +
						msg + "\n" +
                        "Current Balance: Rs " + bal);
            }
            else if (cmd.equals("withdraw")) {
                setArea("Withdraw Operation\n-----------------------\n" +
                        "Name: " + name + "\n" +
                        "Withdrawn: Rs " + amount + "\n" +
                        msg + "\n" +
                        "Current Balance: Rs " + bal);
            }

        } catch (Exception ex) {
            setArea("Error: " + ex.getMessage());
        }
    }

    private void setArea(String text) {
        SwingUtilities.invokeLater(() -> area.setText(text));
    }

    public static void main(String[] args) {
        new BankClient();
    }
}