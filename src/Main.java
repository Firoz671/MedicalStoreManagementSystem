import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Medicine {
    private String name;
    private double price;
    private int quantity;
    private Date expiryDate;

    public Medicine(String name, double price, int quantity, Date expiryDate) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}

class Employee {
    private String name;
    private String designation;

    public Employee(String name, String designation) {
        this.name = name;
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }
}

class SellingInfo {
    private Medicine medicine;
    private int quantitySold;
    private double totalPrice;
    private Date sellingDate;

    public SellingInfo(Medicine medicine, int quantitySold, double totalPrice, Date sellingDate) {
        this.medicine = medicine;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
        this.sellingDate = sellingDate;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getSellingDate() {
        return sellingDate;
    }
}


// from generate bill button
class InvoiceGenerator {
    public static void generateBill(List<SellingInfo> sellingInfoList) {
        double totalBill = 0;

        System.out.println("Bill:");
        System.out.println("----------------------------------------------");
        System.out.println("Medicine Name\tQuantity\tPrice\tTotal");
        System.out.println("----------------------------------------------");

        for (SellingInfo sellingInfo : sellingInfoList) {
            Medicine medicine = sellingInfo.getMedicine();
            int quantitySold = sellingInfo.getQuantitySold();
            double price = medicine.getPrice();
            double total = sellingInfo.getTotalPrice();

            totalBill += total;

            System.out.printf("%-15s\t%-8d\t%.2f\t%.2f%n", medicine.getName(), quantitySold, price, total);
        }

        System.out.println("----------------------------------------------");
        System.out.printf("Total Bill: %.2f%n", totalBill);
    }
}

class MedicalStoreManagementSystem extends JFrame implements ActionListener {
    private List<Medicine> medicines;
    private List<Employee> employees;
    private List<SellingInfo> sellingInfoList;

    private JButton addButton;
    private JButton removeButton;
    private JButton searchButton;
    private JButton generateBillButton;
    private JButton showMedicineButton;
    private JButton helpButton;

    public MedicalStoreManagementSystem() {
        medicines = new ArrayList<>();
        employees = new ArrayList<>();
        sellingInfoList = new ArrayList<>();

        addButton = new JButton("Add Medicine");
        addButton.addActionListener(this);
        removeButton = new JButton("Remove Medicine");
        removeButton.addActionListener(this);
        searchButton = new JButton("Search Medicine");
        searchButton.addActionListener(this);
        generateBillButton = new JButton("Generate Bill");
        generateBillButton.addActionListener(this);
        showMedicineButton = new JButton("Show Medicine");
        showMedicineButton.addActionListener(this);
        helpButton = new JButton("Help");
        helpButton.addActionListener(this);


        Dimension buttonSize = new Dimension(200, 50);
        addButton.setPreferredSize(buttonSize);
        removeButton.setPreferredSize(buttonSize);
        searchButton.setPreferredSize(buttonSize);
        generateBillButton.setPreferredSize(buttonSize);
        showMedicineButton.setPreferredSize(buttonSize);
        helpButton.setPreferredSize(buttonSize);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(searchButton);
        panel.add(generateBillButton);
        panel.add(showMedicineButton);
        panel.add(helpButton);

        add(panel);


        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        JLabel headingLabel = new JLabel("Medical Store Management System");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));


        headingPanel.add(headingLabel);


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());


        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.add(headingPanel, BorderLayout.SOUTH);

        add(contentPanel);

        setTitle("Medical Store Management System");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
// button action performed
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addMedicine();
        } else if (e.getSource() == removeButton) {
            removeMedicine();
        } else if (e.getSource() == searchButton) {
            searchMedicine();
        } else if (e.getSource() == generateBillButton) {
            generateBill();
        } else if (e.getSource() == showMedicineButton) {
            showMedicine();
        } else if (e.getSource() == helpButton) {
            contactDeveloper();
        }
    }

    //add medicine
    private void addMedicine() {
        String name = JOptionPane.showInputDialog("Enter medicine name:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Enter medicine price:"));
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter medicine quantity:"));

        String dateString = JOptionPane.showInputDialog("Enter expiry date (yyyy-MM-dd):");
        Date expiryDate;
        try {
            expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format!");
            return;
        }

        Medicine medicine = new Medicine(name, price, quantity, expiryDate);
        medicines.add(medicine);

        JOptionPane.showMessageDialog(this, "Medicine added successfully!");
    }

    //remove medicine
    private void removeMedicine() {
        String name = JOptionPane.showInputDialog("Enter medicine name:");

        Medicine medicine = searchMedicine(name);
        if (medicine != null) {
            medicines.remove(medicine);
            JOptionPane.showMessageDialog(this, "Medicine removed successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Medicine not found!");
        }
    }

    //search medicine
    private void searchMedicine() {
        String name = JOptionPane.showInputDialog("Enter medicine name:");

        Medicine medicine = searchMedicine(name);
        if (medicine != null) {
            StringBuilder message = new StringBuilder();
            message.append("Medicine found!\n")
                    .append("Name: ").append(medicine.getName()).append("\n")
                    .append("Price: ").append(medicine.getPrice()).append("\n")
                    .append("Quantity: ").append(medicine.getQuantity()).append("\n")
                    .append("Expiry Date: ").append(new SimpleDateFormat("yyyy-MM-dd").format(medicine.getExpiryDate()));

            JOptionPane.showMessageDialog(this, message.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Medicine not found!");
        }
    }

    private Medicine searchMedicine(String name) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }


    //generate bill method
    private void generateBill() {
        List<SellingInfo> sellingInfoList = new ArrayList<>();

        while (true) {
            String name = JOptionPane.showInputDialog("Enter medicine name (or 'done' to finish):");
            if (name.equalsIgnoreCase("done")) {
                break;
            }

            int quantitySold = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity sold:"));

            Medicine medicine = searchMedicine(name);
            if (medicine != null) {
                if (medicine.getQuantity() >= quantitySold) {
                    double price = medicine.getPrice();
                    double totalPrice = price * quantitySold;
                    Date sellingDate = new Date();

                    SellingInfo sellingInfo = new SellingInfo(medicine, quantitySold, totalPrice, sellingDate);
                    sellingInfoList.add(sellingInfo);

                    medicine.setQuantity(medicine.getQuantity() - quantitySold);
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient quantity!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Medicine not found!");
            }
        }

        if (!sellingInfoList.isEmpty()) {
            InvoiceGenerator.generateBill(sellingInfoList);
        } else {
            JOptionPane.showMessageDialog(this, "No medicines sold!");
        }
    }
    // show medicine
    private void showMedicine() {
        StringBuilder message = new StringBuilder();
        message.append("Medicine List:\n");
        for (Medicine medicine : medicines) {
            message.append("Name: ").append(medicine.getName()).append("\t")
                    .append("Price: ").append(medicine.getPrice()).append("\t")
                    .append("Quantity: ").append(medicine.getQuantity()).append("\t")
                    .append("Expiry Date: ").append(new SimpleDateFormat("yyyy-MM-dd").format(medicine.getExpiryDate()))
                    .append("\n");
        }
        JOptionPane.showMessageDialog(this, message.toString());
    }

    //help button funcitonality
    private void contactDeveloper() {
        JOptionPane.showMessageDialog(this, "Contact the developer for assistance:\n" +
                "Email: firozalmahmud671@.com\n" +
                "Phone: +1-123-456-7890");
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MedicalStoreManagementSystem();
            }
        });
    }
}
