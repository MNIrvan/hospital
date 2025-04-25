import java.util.ArrayList; // menggunakan library menampung data pasien
import java.util.Scanner; //menggunakan library scanner untuk input data

public class hospital { // Membuat class hospital
    private static ArrayList<Patient> patientQueue = new ArrayList<>(); // Membuat ArrayList patientQueue untuk menampung data pasien
    private static Scanner scanner = new Scanner(System.in); // Membuat variable scanner untuk membaca input dari user

    public static void main(String[] args) { // Method utama untuk menjalankan program
        boolean running = true; // Menjalankan loop program

        System.out.println("Welcome to Hospital Queue Management System"); // Menampilkan pesan sambutan "Selamat datang di sistem antrian rumah sakit"

        while (running) { // Selama program berjalan
            displayMenu(); // Menampilkan menu
            int choice = getValidIntInput("Enter your choice: "); // Mengambil input pilihan dari user

            switch (choice) { // Menggunakan switch-case untuk menentukan pilihan
                case 1: // Pilihan 1
                    addPatient(); // Menambahkan pasien baru
                    break; // Memberhentikan program
                case 2: // Pilihan 2
                    serveNextPatient(); // Melayani pasien berikutnya
                    break; // Memberhentikan program
                case 3: // Pilihan 3
                    displayQueue(); // Menampilkan antrian pasien saat ini
                    break; // Memberhentikan program
                case 4: // Pilihan 4
                    updatePriority(); // Memperbarui prioritas pasien
                    break; // Memberhentikan program
                case 5: // Pilihan 5
                    searchPatient(); // Mencari pasien berdasarkan nama
                    break; // Memberhentikan program
                case 6: // Pilihan 6
                    System.out.println("Thank you for using Hospital Queue Management System. Goodbye!"); // Menampilkan pesan terima kasih dan keluar dari program
                    running = false; // Menghentikan loop program
                    break; // Memberhentikan program
                default: // Pilihan tidak valid
                    System.out.println("Invalid choice. Please try again."); // // Menampilkan pesan input tidak valid
            }
        }

        scanner.close(); // Menutup scanner setelah program selesai
    }

    private static void displayMenu() { // // Menampilkan menu pilihan
        System.out.println("\n===== HOSPITAL QUEUE SYSTEM ====="); // Menampilkan judul sistem antrian rumah sakit
        System.out.println("1. Add a new patient to the queue"); // Menampilkan pilihan untuk menambahkan pasien baru
        System.out.println("2. Serve next patient"); // Menampilkan pilihan untuk melayani pasien berikutnya
        System.out.println("3. Display current queue"); // Menampilkan pilihan untuk menampilkan antrian pasien saat ini
        System.out.println("4. Update patient priority"); // Menampilkan pilihan untuk memperbarui prioritas pasien
        System.out.println("5. Search for a patient"); // Menampilkan pilihan untuk mencari pasien berdasarkan nama
        System.out.println("6. Exit"); // Menampilkan pilihan untuk keluar dari program
        System.out.println("================================="); // Menampilkan garis pemisah
    }

    private static void addPatient() { // // Menambahkan pasien baru ke dalam antrian
        String name = getValidStringInput("Enter patient's name: "); // Mengambil input nama pasien
        int age = getValidIntInput("Enter patient's age: "); // Mengambil input usia pasien
        String condition = getValidStringInput("Enter patient's condition: "); // Mengambil input kondisi pasien
        int priority = getValidIntInRange("Enter priority (1 = Critical, 5 = Non-urgent): ", 1, 5); // Mengambil input prioritas pasien dengan validasi

        Patient newPatient = new Patient(name, age, condition, priority); // Membuat objek pasien baru dengan data yang diinputkan
        patientQueue.add(newPatient); // Menambahkan pasien baru ke dalam antrian
        System.out.println("Patient added to the queue."); // Menampilkan pesan pasien berhasil ditambahkan
    }

    private static void serveNextPatient() { // Melayani pasien berikutnya dalam antrian
        if (patientQueue.isEmpty()) { // Jika variable patienQueue kosong
            System.out.println("No patients in the queue."); // Menampilkan pesan tidak ada pasien dalam antrian
            return; // Mengembalikan nilai
        }

        Patient nextPatient = patientQueue.get(0); // Mengambil pasien pertama dalam antrian
        for (Patient p : patientQueue) { // // Menggunakan loop untuk mencari pasien dengan prioritas tertinggi
            if (p.getPriority() < nextPatient.getPriority()) { // Jika prioritas pasien lebih tinggi dari pasien berikutnya
                nextPatient = p; // Mengambil pasien dengan prioritas tertinggi
            }
        }

        patientQueue.remove(nextPatient); // Menghapus pasien dari antrian
        System.out.println("Serving patient: " + nextPatient.getName()); // Menampilkan pesan pasien yang dilayani
    }

    private static void displayQueue() {  // Menampilkan antrian pasien saat ini
        if (patientQueue.isEmpty()) { // Jika variable patienQueue kosong
            System.out.println("Queue is empty."); // Menampilkan pesan antrian kosong
            return; // Mengembalikan nilai
        }

        System.out.println("\n--- Current Patient Queue ---"); // Menampilkan judul antrian pasien saat ini
        for (Patient p : patientQueue) { // Menggunakan loop untuk menampilkan data pasien dalam antrian
            System.out.println("Name: " + p.getName() + // Menampilkan nama pasien 
                               ", Age: " + p.getAge() + // Menampilkan usia pasien 
                               ", Condition: " + p.getCondition() + // Menampilkan kondisi pasien 
                               ", Priority: " + getPriorityText(p.getPriority())); // Menampilkan prioritas pasien
        }
    }

    private static void updatePriority() {  // Memperbarui prioritas pasien
        if (patientQueue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        String name = getValidStringInput("Enter the name of the patient to update: ");
        boolean found = false;

        for (Patient p : patientQueue) {
            if (p.getName().equalsIgnoreCase(name)) {
                int newPriority = getValidIntInRange("Enter new priority (1 = Critical, 5 = Non-urgent): ", 1, 5);
                p.setPriority(newPriority);
                System.out.println("Priority updated for " + p.getName());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Patient not found.");
        }
    }

    private static void searchPatient() {
        if (patientQueue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        String name = getValidStringInput("Enter the name to search: ");
        boolean found = false;

        for (Patient p : patientQueue) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("Patient found:");
                System.out.println("Name: " + p.getName() +
                                   ", Age: " + p.getAge() +
                                   ", Condition: " + p.getCondition() +
                                   ", Priority: " + getPriorityText(p.getPriority()));
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Patient not found.");
        }
    }

    private static String getPriorityText(int priority) {
        switch (priority) {
            case 1: return "1-Critical";
            case 2: return "2-Urgent";
            case 3: return "3-High";
            case 4: return "4-Medium";
            case 5: return "5-Non-urgent";
            default: return "Unknown";
        }
    }

    private static int getValidIntInput(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return value;
    }

    private static int getValidIntInRange(String prompt, int min, int max) {
        int value;
        while (true) {
            value = getValidIntInput(prompt);
            if (value >= min && value <= max) {
                break;
            }
            System.out.println("Please enter a value between " + min + " and " + max + ".");
        }
        return value;
    }

    private static String getValidStringInput(String prompt) {
        String value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                break;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
        return value;
    }
}

class Patient {
    private String name;
    private int age;
    private String condition;
    private int priority; // 1 (Critical) to 5 (Non-urgent)

    public Patient(String name, int age, String condition, int priority) {
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCondition() {
        return condition;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}