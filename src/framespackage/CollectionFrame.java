package framespackage;

import graphicModals.CollectionFrameModal;
import graphicModals.Colors;
import graphicModals.FontFamilys;
import connection.HandleNewCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CollectionFrame {
    private int userId;
    private CollectionFrameModal collectionFrame;

    public CollectionFrame(int userId) {
        this.userId = userId;
        collectionFrame = new CollectionFrameModal(300, 600, "Collection");

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Colors.MAIN_THEME);

        JButton viewCollectionsButton = new JButton("View Collections");
        JButton newCollectionButton = new JButton("New Collection");

        viewCollectionsButton.setBackground(Colors.SUB_THEME_1);
        viewCollectionsButton.setFont(FontFamilys.BOLD);
        newCollectionButton.setBackground(Colors.SUB_THEME_1);
        newCollectionButton.setFont(FontFamilys.BOLD);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(viewCollectionsButton, gbc);

        gbc.gridy++;
        buttonPanel.add(newCollectionButton, gbc);

        collectionFrame.addComponent(buttonPanel, BorderLayout.CENTER);
        viewCollectionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleViewCollections();
            }
        });

        newCollectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNewCollectionWindow();
            }
        });

        collectionFrame.setVisible(true);
    }

    private void handleViewCollections() {
        new CollectionViewFrame(userId);
    }

    private void handleNewCollectionWindow() {

        JDialog newCollectionDialog = new JDialog(collectionFrame, "New Collection", true);
        newCollectionDialog.setSize(300, 200);
        newCollectionDialog.setLayout(new GridBagLayout());

        JLabel nameLabel = new JLabel("Enter collection name:");
        JTextField nameField = new JTextField(15);
        JButton okButton = new JButton("OK");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        newCollectionDialog.add(nameLabel, gbc);

        gbc.gridy++;
        newCollectionDialog.add(nameField, gbc);

        gbc.gridy++;
        newCollectionDialog.add(okButton, gbc);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String collectionName = nameField.getText().trim();
                if (!collectionName.isEmpty()) {
                    new HandleNewCollection(userId, collectionName);
                    newCollectionDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(newCollectionDialog, "Please enter a collection name.");
                }
            }
        });

        newCollectionDialog.setLocationRelativeTo(collectionFrame);
        newCollectionDialog.setVisible(true);
    }
}
