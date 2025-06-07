package View;

import Items.Item;
import Model.Player;
import Model.StateEngine;

import javax.swing.*;
import java.awt.*;

//Class responsible for drawing Game

public class GameGraphics extends JPanel {

    private Player player;
    private StateEngine engine;

    public GameGraphics(Player player, StateEngine engine) {
        this.engine = engine;
        this.player = player;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D painter = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        painter.addRenderingHints(rh);
        painter.addRenderingHints(rh2);

        painter.setColor(Color.BLACK);
        engine.getPlayer_location().draw(painter);
        player.draw(painter);

        //level panel

        painter.setFont(new Font("Arial", Font.BOLD, 16));
        String levelText = "Current level: " + engine.getLevels_count();

        FontMetrics fm = painter.getFontMetrics();
        int textWidth = fm.stringWidth(levelText);
        int textHeight = fm.getHeight();

        int panel_width = textWidth + 20;
        int panel_height = textHeight + 10;

        int panel_pos_x = (getWidth() - panel_width) / 2;
        int panel_pos_y = getHeight() - panel_height;

        Color panel_fill_color = Color.WHITE;
        Color panel_border_color = Color.BLACK;

        painter.setColor(panel_fill_color);
        painter.fillRoundRect(panel_pos_x, panel_pos_y, panel_width, panel_height, 12, 12);

        painter.setStroke(new BasicStroke(3));
        painter.setColor(panel_border_color);
        painter.drawRoundRect(panel_pos_x, panel_pos_y, panel_width, panel_height, 12, 12);

        //inventory

        painter.setColor(Color.BLACK);
        int text_pos_x = panel_pos_x + (panel_width - textWidth) / 2;
        int text_pos_y = panel_pos_y + (panel_height + textHeight / 2) / 2;
        painter.drawString(levelText, text_pos_x, text_pos_y);

        int inventory_panel_width = 200;
        int inventory_panel_height = 40;
        int inventory_panel_x = (getWidth() - inventory_panel_width) / 2;
        int inventory_panel_y = 0;

        painter.setColor(Color.gray);
        painter.fillRoundRect(inventory_panel_x, inventory_panel_y, inventory_panel_width, inventory_panel_height, 12, 12);

        int slot_size = 32;
        int slot_padding = 6;
        int slot_x = inventory_panel_x + slot_padding;
        int slot_y = inventory_panel_y + 4;

        for (int i = 0; i < 5; i++) {
            painter.setColor(Color.WHITE);
            painter.fillRect(slot_x, slot_y, slot_size, slot_size);

            painter.setColor(Color.BLACK);
            painter.setStroke(new BasicStroke(3));
            painter.drawRect(slot_x, slot_y, slot_size, slot_size);

            Item item = engine.getInventory().getItem(i);
            if (item != null && item.getIcon() != null) {
                painter.drawImage(item.getIcon(), slot_x + 2, slot_y + 2, slot_size - 4, slot_size - 4, null);
            }

            slot_x += slot_size + slot_padding;
        }
    }
}
