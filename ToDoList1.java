import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ToDoList1 {
    private ToDoList toDoList;
    private JFrame frame;
    private JTextField taskField;
    private JPanel taskListPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton completeButton;

    public ToDoList1() {
        toDoList = new ToDoList();
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("ToDo List Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        taskField = new JTextField(50);
        taskField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        addButton = new JButton("Add Task");
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(150, 40));
        deleteButton = new JButton("Delete Task");
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(150, 40));
        completeButton = new JButton("Mark as Completed");
        completeButton.setBackground(Color.BLACK);
        completeButton.setForeground(Color.RED);
        completeButton.setPreferredSize(new Dimension(180, 40));

        inputPanel.add(taskField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(completeButton);

        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(taskListPanel);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new AddTaskListener());
        deleteButton.addActionListener(new DeleteTaskListener());
        completeButton.addActionListener(new CompleteTaskListener());

        frame.pack();
        frame.setVisible(true);
    }

    private void addTask() {
        String taskDescription = taskField.getText();
        if (!taskDescription.isEmpty()) {
            toDoList.addTask(taskDescription);
            taskField.setText("");
            updateTaskList();
        }
    }

    private class AddTaskListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addTask();
        }
    }

    private class DeleteTaskListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String taskNumber = JOptionPane.showInputDialog("Enter the task number to delete:");
            if (taskNumber != null) {
                int deleteIndex = Integer.parseInt(taskNumber) - 1;
                toDoList.deleteTask(deleteIndex);
                updateTaskList();
            }
        }
    }

    private class CompleteTaskListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String taskNumber = JOptionPane.showInputDialog("Enter the task number to mark as completed:");
            if (taskNumber != null) {
                int completeIndex = Integer.parseInt(taskNumber) - 1;
                toDoList.markTaskAsCompleted(completeIndex);
                updateTaskList();
            }
        }
    }

    private void updateTaskList() {
        taskListPanel.removeAll();
        List<Task> tasks = toDoList.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            JLabel taskLabel = new JLabel((i + 1) + ". " + task.getDescription());
            taskLabel.setOpaque(true);
            if (task.isCompleted()) {
                taskLabel.setBackground(Color.decode("#ec702d"));
                taskLabel.setForeground(Color.WHITE);
            } else {
                taskLabel.setBackground(Color.WHITE);
                taskLabel.setForeground(Color.BLACK);
            }
            taskListPanel.add(taskLabel);
        }
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoList1();
            }
        });
    }
}
