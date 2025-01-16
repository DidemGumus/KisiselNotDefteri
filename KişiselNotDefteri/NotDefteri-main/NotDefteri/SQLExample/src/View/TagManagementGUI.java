package View;

import Controller.TagController;
import Model.Tag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TagManagementGUI extends JFrame {
    private TagController tagController;

    public TagManagementGUI(int userId) {
        this.tagController = new TagController();

        setTitle("Etiket Yönetimi");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel elemanları
        JTextField tagField = new JTextField(20);
        JButton addButton = new JButton("Ekle");
        JButton deleteButton = new JButton("Sil");
        JButton editButton = new JButton("Düzenle");
        JList<Tag> tagList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(tagList);

        // Etiket ekleme işlemi
        addButton.addActionListener(e -> {
            String tagName = tagField.getText().trim();
            if (!tagName.isEmpty()) {
                Tag newTag = new Tag(0, tagName, userId);
                if (tagController.addTag(newTag)) {
                    JOptionPane.showMessageDialog(this, "Etiket başarıyla eklendi.");
                    loadTags(tagList, userId);
                } else {
                    JOptionPane.showMessageDialog(this, "Etiket eklenemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Etiket adı boş olamaz.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Etiket silme işlemi
        deleteButton.addActionListener(e -> {
            Tag selectedTag = tagList.getSelectedValue();
            if (selectedTag != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bu etiketi silmek istediğinizden emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (tagController.deleteTag(selectedTag.getId())) {
                        JOptionPane.showMessageDialog(this, "Etiket başarıyla silindi.");
                        loadTags(tagList, userId);
                    } else {
                        JOptionPane.showMessageDialog(this, "Etiket silinemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen silmek için bir etiket seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Etiket düzenleme işlemi
        editButton.addActionListener(e -> {
            Tag selectedTag = tagList.getSelectedValue();
            if (selectedTag != null) {
                String newName = JOptionPane.showInputDialog(this, "Yeni Etiket Adı:", selectedTag.getName());
                if (newName != null && !newName.trim().isEmpty()) {
                    if (tagController.updateTag(selectedTag.getId(), newName.trim())) {
                        JOptionPane.showMessageDialog(this, "Etiket başarıyla güncellendi.");
                        loadTags(tagList, userId);
                    } else {
                        JOptionPane.showMessageDialog(this, "Etiket güncellenemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Etiket adı boş olamaz.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen düzenlemek için bir etiket seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Etiket listesini yükleme
        loadTags(tagList, userId);

        // Layout
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Etiket Adı:"));
        topPanel.add(tagField);
        topPanel.add(addButton);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(deleteButton);
        bottomPanel.add(editButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void loadTags(JList<Tag> tagList, int userId) {
        List<Tag> tags = tagController.getTagsByUserId(userId);
        DefaultListModel<Tag> listModel = new DefaultListModel<>();
        for (Tag tag : tags) {
            listModel.addElement(tag);
        }
        tagList.setModel(listModel);
    }
}
