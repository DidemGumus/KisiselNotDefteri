package View;
import Command.*;
import Controller.NoteController;
import Controller.TagController;
import Model.Note;
import Model.Tag;
import analytics.NoteAddedMetric;
import analytics.NoteDeletedMetric;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import Controller.NoteSubject;
import Controller.UserObserver;
import Strategy.NoteFilterContext;
import Strategy.StatusFilterStrategy;
import Strategy.TitleFilterStrategy;
import ThemeFactory.*;
public class NoteManagementGUI extends JFrame {
    private NoteController noteController;
    private TagController tagController;
    private int userId;
    private DefaultListModel<Note> noteListModel;
    private JList<Note> noteList;
    private NoteSubject noteSubject;
    private CommandManager commandManager;

    private NoteAddedMetric noteAddedMetric;
    private NoteDeletedMetric noteDeletedMetric;
    public NoteManagementGUI(int userId) {
        this.userId = userId;
        this.noteController = new NoteController();
        this.tagController = new TagController();
        this.noteSubject = new NoteSubject();
        this.commandManager = new CommandManager();
        this.noteAddedMetric = new NoteAddedMetric();
        this.noteDeletedMetric = new NoteDeletedMetric();

        noteSubject.addObserver(new UserObserver("Kullanıcı " + userId, userId));
        setTitle("Not Yönetimi");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);




        JPanel mainPanel = new JPanel(new BorderLayout());


        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<String> themeComboBox = new JComboBox<>(new String[]{"Light", "Dark"});

        themeComboBox.addActionListener(e -> {
            String selectedTheme = (String) themeComboBox.getSelectedItem();
            AbstractTheme theme = ThemeFactory.getTheme(selectedTheme.toLowerCase());
            if (theme != null) {
                theme.applyTheme(this); // Seçilen temayı uygula
                SwingUtilities.updateComponentTreeUI(this); // Arayüzü güncelle
                JOptionPane.showMessageDialog(this, selectedTheme + " teması uygulandı!", "Tema Değiştirildi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Seçilen tema bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        themePanel.add(new JLabel("Tema: "));
        themePanel.add(themeComboBox);


        // Filtreleme Paneli
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filterLabel = new JLabel("Filtre Türü:");
        JComboBox<String> filterTypeComboBox = new JComboBox<>(new String[]{"Duruma Göre", "Başlığa Göre"});
        JTextField filterInputField = new JTextField(15);
        JButton filterButton = new JButton("Filtrele");

        filterPanel.add(filterLabel);
        filterPanel.add(filterTypeComboBox);
        filterPanel.add(filterInputField);
        filterPanel.add(filterButton);

        // Tema ve Filtreleme Panellerini Birleştir
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(themePanel, BorderLayout.NORTH);
        northPanel.add(filterPanel, BorderLayout.SOUTH);

        mainPanel.add(northPanel, BorderLayout.NORTH);

        // Not listesi ve diğer metotlar
        noteListModel = new DefaultListModel<>();
        noteList = new JList<>(noteListModel);
        refreshNoteList();


        filterButton.addActionListener(e -> {
            String filterType = (String) filterTypeComboBox.getSelectedItem();
            String filterInput = filterInputField.getText().trim();

            if (filterType.equals("Duruma Göre")) {
                filterByStatus(filterInput);
            } else if (filterType.equals("Başlığa Göre")) {
                filterByTitle(filterInput);
            }
        });






        // Not başlıkları listesi ekranı
        noteListModel = new DefaultListModel<>();
        noteList = new JList<>(noteListModel);
        noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        noteList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Note note = (Note) value;
                List<Tag> tags = tagController.getTagsForNote(note.getId());

                // Etiketleri virgülle ayırarak metin oluşturun
                StringBuilder tagNames = new StringBuilder();
                for (Tag tag : tags) {
                    tagNames.append(tag.getName()).append(", ");
                }
                if (tagNames.length() > 0) {
                    tagNames.setLength(tagNames.length() - 2); // Son virgülü kaldır
                }

                // Not başlığı ve durum bilgisi
                JLabel label = new JLabel("<html><b>" + note.getTitle() + "</b> <span style='color:blue'>[" + tagNames + "]</span> <span style='color:green'>(" + note.getStatus() + ")</span></html>");
                label.setOpaque(true);
                if (isSelected) {
                    label.setBackground(Color.LIGHT_GRAY);
                    label.setForeground(Color.BLACK);
                }
                return label;
            }
        });



        // Not başlığına tıklanıldığında detay ekranı açılır
        noteList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    // Tek tıklama: Seçim işlemi
                    Note selectedNote = noteList.getSelectedValue();
                    if (selectedNote != null) {
                        System.out.println("Seçilen Not: " + selectedNote.getTitle());
                        // Seçme işlemi burada gerçekleşebilir
                    }
                } else if (e.getClickCount() == 2) {
                    // Çift tıklama: İçeriği açma işlemi
                    Note selectedNote = noteList.getSelectedValue();
                    if (selectedNote != null) {
                        openNoteDetailWindow(selectedNote); // İçeriği açma
                    }
                }
            }
        });


        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addNoteButton = new JButton("Yeni Not Ekle");
        JButton editNoteButton = new JButton("Notu Düzenle");
        JButton deleteNoteButton = new JButton("Notu Sil");
        JButton changeStateButton = new JButton("Durumu Değiştir");
        JButton undoButton = new JButton("Geri Al");
        JButton redoButton = new JButton("Yeniden Yap");
        // İstatistikleri Göster Butonu
        JButton showStatsButton = new JButton("İstatistikleri Göster");

        showStatsButton.addActionListener(e -> {
            int addedNotes = noteAddedMetric.getMetricForUser(userId);
            int deletedNotes = noteDeletedMetric.getMetricForUser(userId);

            JOptionPane.showMessageDialog(this,
                    "Toplam Eklenen Not: " + addedNotes +
                            "\nToplam Silinen Not: " + deletedNotes,
                    "Kullanıcı İstatistikleri",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // İstatistik butonunu ekleyin
        buttonPanel.add(showStatsButton);

        JButton manageTagsButton = new JButton("Etiket Yönetimi"); // Yeni buton
        JButton logoutButton = new JButton("Çıkış Yap"); // Çıkış butonu
        // Buton işlevleri
        addNoteButton.addActionListener(e -> addNewNote());
        editNoteButton.addActionListener(e -> editSelectedNote());
        deleteNoteButton.addActionListener(e -> deleteSelectedNote());
        changeStateButton.addActionListener(e -> changeNoteState());
        undoButton.addActionListener(e -> commandManager.undo());
        redoButton.addActionListener(e -> commandManager.redo());
        undoButton.addActionListener(e -> {
            try {
                commandManager.undo();
                refreshNoteList(); // Listeyi yenileyin
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Geri alma işlemi başarısız: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
        redoButton.addActionListener(e -> {
            try {
                commandManager.redo();
                refreshNoteList(); // Listeyi yenileyin
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Yeniden yapma işlemi başarısız: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
        undoButton.addActionListener(e -> {
            try {
                commandManager.undo();
                refreshNoteList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Geri alma işlemi başarısız: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        manageTagsButton.addActionListener(e -> new TagManagementGUI(userId)); // Etiket Yönetimi ekranını aç
        logoutButton.addActionListener(e -> logout()); // Çıkış işlemi
        // Butonları ekle
        buttonPanel.add(addNoteButton);
        buttonPanel.add(editNoteButton);
        buttonPanel.add(deleteNoteButton);
        buttonPanel.add(changeStateButton); // Durum değiştirme butonu eklendi
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(manageTagsButton); // Yeni butonu ekleyin
        buttonPanel.add(logoutButton);

        // Notları yükle
        refreshNoteList();

        mainPanel.add(new JScrollPane(noteList), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
    private void changeNoteState() {
        Note selectedNote = noteList.getSelectedValue();
        if (selectedNote == null) {
            JOptionPane.showMessageDialog(this, "Lütfen bir not seçin!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] states = {"Taslak", "Tamamlandı"};
        String newState = (String) JOptionPane.showInputDialog(
                this,
                "Yeni durumu seçin:",
                "Durum Değiştir",
                JOptionPane.QUESTION_MESSAGE,
                null,
                states,
                states[0]);

        if (newState != null && !newState.isEmpty()) {
            boolean success = noteController.changeNoteState(selectedNote.getId(), newState.toLowerCase());
            if (success) {
                noteSubject.notifyObservers("Notun durumu değişti: " + newState, userId);
                JOptionPane.showMessageDialog(this, "Durum başarıyla değiştirildi.");
                refreshNoteList();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Durum değiştirilemedi. Hata: Note ID = " + selectedNote.getId() + ", New State = " + newState,
                        "Hata",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }


    private void openNoteDetailWindow(Note note) {
        JDialog detailDialog = new JDialog(this, "Not Detayı", true);
        detailDialog.setSize(400, 300);
        detailDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel noteTitleLabel = new JLabel(note.getTitle());
        JTextArea noteContentArea = new JTextArea(note.getContent());
        noteContentArea.setEditable(false);

        panel.add(noteTitleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(noteContentArea), BorderLayout.CENTER);

        detailDialog.add(panel);
        detailDialog.setLocationRelativeTo(this);
        detailDialog.setVisible(true);
    }

    private void addNewNote() {
        if (!noteController.isUserExists(userId)) {
            JOptionPane.showMessageDialog(this, "Kullanıcı bulunamadı. Lütfen geçerli bir kullanıcı ID'si girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog newNoteDialog = new JDialog(this, "Yeni Not Ekle", true);
        newNoteDialog.setSize(400, 400);
        newNoteDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Başlık ve İçerik
        JTextField titleField = new JTextField(20);
        JTextArea contentArea = new JTextArea(10, 30);
        panel.add(new JLabel("Başlık:"), BorderLayout.NORTH);
        panel.add(titleField, BorderLayout.NORTH);
        panel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        // Etiket Seçimi
        JPanel tagPanel = new JPanel(new GridLayout(0, 1));
        ButtonGroup tagGroup = new ButtonGroup();
        List<Tag> tags = tagController.getTagsByUserId(userId);

        // Eğer liste boşsa mesaj göster
        if (tags.isEmpty()) {
            JLabel noTagsLabel = new JLabel("<html>Kullanılabilir etiket yok. <br>Önce <b>Etiket Yönetimi</b> kısmından etiket ekleyin.</html>");
            tagPanel.add(noTagsLabel);
        } else {
            // Eğer liste doluysa etiketleri ekleyin
            for (Tag tag : tags) {
                JRadioButton tagButton = new JRadioButton(tag.getName());
                tagButton.setActionCommand(String.valueOf(tag.getId()));
                tagGroup.add(tagButton);
                tagPanel.add(tagButton);
            }
        }

        panel.add(tagPanel, BorderLayout.EAST);

        // Kaydet ve İptal Butonları
        JButton saveButton = new JButton("Kaydet");
        JButton cancelButton = new JButton("İptal");

        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            String content = contentArea.getText();
            ButtonModel selectedTag = tagGroup.getSelection();

            if (title.isEmpty() || content.isEmpty() || selectedTag == null) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun ve bir etiket seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int tagId = Integer.parseInt(selectedTag.getActionCommand());



            // Notu ekleyin ve komut yöneticisine kaydedin
            if (noteController.addNote(userId, title, content, "Active")) {
                noteAddedMetric.recordEvent(userId); // Kullanıcı istatistiği güncelleniyor
                refreshNoteList();

                // Yeni eklenen notu al ve etiketi ekle
                Note addedNote = noteController.getNotesByUserId(userId).get(noteController.getNotesByUserId(userId).size() - 1);
                tagController.addTagToNote(addedNote.getId(), tagId);

                // Komut yöneticisine kaydedin
                noteSubject.notifyObservers("Yeni bir not eklendi: " + title, userId);

                JOptionPane.showMessageDialog(this, "Not başarıyla eklendi.");
                refreshNoteList(); // Listeyi yenileyin
                newNoteDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Not eklenemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> newNoteDialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        newNoteDialog.add(panel);
        newNoteDialog.setLocationRelativeTo(this);
        newNoteDialog.setVisible(true);
    }



    private void editSelectedNote() {
        Note selectedNote = noteList.getSelectedValue();
        if (selectedNote == null) {
            JOptionPane.showMessageDialog(this, "Lütfen bir not seçin!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog editNoteDialog = new JDialog(this, "Notu Düzenle", true);
        editNoteDialog.setSize(400, 400);
        editNoteDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JTextField titleField = new JTextField(selectedNote.getTitle(), 20);
        JTextArea contentArea = new JTextArea(selectedNote.getContent(), 10, 30);

        panel.add(new JLabel("Başlık:"), BorderLayout.NORTH);
        panel.add(titleField, BorderLayout.NORTH);
        panel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        JPanel tagPanel = new JPanel(new GridLayout(0, 1));
        ButtonGroup tagGroup = new ButtonGroup();
        List<Tag> tags = tagController.getTagsByUserId(userId);

        if (tags.isEmpty()) {
            JLabel noTagsLabel = new JLabel("<html>Kullanılabilir etiket yok. <br>Önce <b>Etiket Yönetimi</b> kısmından etiket ekleyin.</html>");
            tagPanel.add(noTagsLabel);
        } else {
            for (Tag tag : tags) {
                JRadioButton tagButton = new JRadioButton(tag.getName());
                tagButton.setActionCommand(String.valueOf(tag.getId()));
                if (tagController.getTagsForNote(selectedNote.getId()).stream().anyMatch(t -> t.getId() == tag.getId())) {
                    tagButton.setSelected(true);
                }
                tagGroup.add(tagButton);
                tagPanel.add(tagButton);
            }
        }
        panel.add(tagPanel, BorderLayout.EAST);

        JButton saveButton = new JButton("Kaydet");
        JButton cancelButton = new JButton("İptal");

        saveButton.addActionListener(e -> {
            String newTitle = titleField.getText();
            String newContent = contentArea.getText();
            ButtonModel selectedTag = tagGroup.getSelection();

            if (newTitle.isEmpty() || newContent.isEmpty() || selectedTag == null) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun ve bir etiket seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int tagId = Integer.parseInt(selectedTag.getActionCommand());
            if (noteController.updateNote(selectedNote.getId(), newTitle, newContent, "Active")) {
                tagController.addTagToNote(selectedNote.getId(), tagId);
                noteSubject.notifyObservers("Not güncellendi: " + newTitle, userId); // Bildirim gönderiliyor
                JOptionPane.showMessageDialog(this, "Not başarıyla güncellendi.");
                editNoteDialog.dispose();
                refreshNoteList();
            } else {
                JOptionPane.showMessageDialog(this, "Not güncellenemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
            }


        });

        cancelButton.addActionListener(e -> editNoteDialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        editNoteDialog.add(panel);
        editNoteDialog.setLocationRelativeTo(this);
        editNoteDialog.setVisible(true);
    }
    private void filterByStatus(String status) {
        if (status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen bir durum girin (ör. Taslak, Tamamlandı)", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        NoteFilterContext filterContext = new NoteFilterContext();
        filterContext.setStrategy(new StatusFilterStrategy(status));

        List<Note> allNotes = noteController.getNotesByUserId(userId);
        List<Note> filteredNotes = filterContext.filterNotes(allNotes);

        updateNoteList(filteredNotes);
    }
    private void filterByTitle(String keyword) {
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen bir anahtar kelime girin", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        NoteFilterContext filterContext = new NoteFilterContext();
        filterContext.setStrategy(new TitleFilterStrategy(keyword));

        List<Note> allNotes = noteController.getNotesByUserId(userId);
        List<Note> filteredNotes = filterContext.filterNotes(allNotes);

        updateNoteList(filteredNotes);
    }

    private void updateNoteList(List<Note> notes) {
        noteListModel.clear();
        for (Note note : notes) {
            noteListModel.addElement(note);
        }
    }



    private void deleteSelectedNote() {
        Note selectedNote = noteList.getSelectedValue();
        if (selectedNote == null) {
            JOptionPane.showMessageDialog(this, "Lütfen bir not seçin!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bu notu silmek istediğinizden emin misiniz?");
        if (confirm == JOptionPane.YES_OPTION) {
            noteController.deleteNoteById(selectedNote.getId());
            noteListModel.removeElement(selectedNote);
            noteDeletedMetric.recordEvent(userId); // Kullanıcı istatistiği güncelleniyor
            refreshNoteList();

            noteSubject.notifyObservers("Not silindi: " + selectedNote.getTitle(), userId); // Bildirim gönderiliyor
            JOptionPane.showMessageDialog(this, "Not başarıyla silindi.");
        }
        commandManager.execute(new DeleteNoteCommand(noteController, selectedNote));
        refreshNoteList();
    }




    private void refreshNoteList() {
        List<Note> notes = noteController.getNotesByUserId(userId);
        noteListModel.clear();
        for (Note note : notes) {
            noteListModel.addElement(note); // Not bilgisi listeye ekleniyor
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Çıkmak istediğinizden emin misiniz?", "Çıkış Onayı", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose(); // Mevcut pencereyi kapatın
        }
    }
}