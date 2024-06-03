package framespackage;

import graphicModals.JFrameModal;
import connection.PrepareData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CollectionViewFrame {
    private JFrameModal collectionViewFrame;

    public CollectionViewFrame(int userId) {
        collectionViewFrame = new JFrameModal(300, 600, "Collections");

        List<String> collections = PrepareData.getCollections(userId);

        JPanel collectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        for (String collection : collections) {
            JButton collectionButton = new JButton(collection);

            collectionButton.setPreferredSize(new Dimension(100, 50));

            collectionButton.addActionListener(e -> handleCollectionClick(userId, collection));
            collectionPanel.add(collectionButton);
        }

        collectionViewFrame.addComponent(collectionPanel, BorderLayout.CENTER);

        collectionViewFrame.setVisible(true);
    }

    private void handleCollectionClick(int userId, String collectionName) {
        collectionViewFrame.dispose();
        new SpecificCollectionValuesFrame(userId, collectionName);
    }
}
