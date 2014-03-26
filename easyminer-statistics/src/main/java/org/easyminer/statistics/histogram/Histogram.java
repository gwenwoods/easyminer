package org.easyminer.statistics.histogram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.easyminer.io.data.CateFieldStat;

public final class Histogram extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int PAD = 40;

    private static final int IMAGE_W = 1024;

    private static final int IMAGE_H = 768;

    private static final Color[] DATA_COLOR = new Color[10];

    private final List<CateFieldStat> data = new ArrayList<CateFieldStat>();

    static {
        DATA_COLOR[0] = new Color(10, 150, 10);
        DATA_COLOR[1] = Color.RED;
        DATA_COLOR[2] = Color.BLUE;
    }

    public Histogram(List<CateFieldStat> inputData) {
        super();
        this.data.addAll(inputData);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        List<String> levels = new ArrayList<String>(findLevels());

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // -------------------------------
        // Draw background
        g2.setColor(Color.lightGray);
        g2.fillRect(0, 0, w, h);

        g2.setColor(Color.BLACK);
        // Draw ordinate (y-axis)
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
        // Draw abscissa (x-axis)
        g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));

        // ----------------------------------
        double levelIncre = (double) (w - 2 * PAD) / levels.size();
        int barWidth = (int) (levelIncre / (data.size() + 1));
        double scale = (h - 2 * PAD) / findYMax(data);
        double xAxisMargin = levelIncre * 0.1;

        for (int i = 0; i < levels.size(); i++) {
            String cate = levels.get(i);
            double xLevel = PAD + xAxisMargin + i * levelIncre;
            for (int j = 0; j < data.size(); j++) {

                double x = xLevel + j * barWidth;

                Double normalizedCount = data.get(j).getCateNorCountMap().get(cate);
                if (normalizedCount == null) {
                    normalizedCount = 0.0;
                }

                double goodBarLength = scale * normalizedCount;

                g2.setPaint(DATA_COLOR[j]);
                g2.fill(new Rectangle2D.Double(x, h - PAD - goodBarLength, barWidth, goodBarLength));

                Double goodBarPctValue = Math.round(normalizedCount * 10000) / 100.0;
                String goodBarPct = goodBarPctValue > 0.1 ? (goodBarPctValue.toString() + " %") : "";
                g2.drawString(goodBarPct, (int) x, h - PAD - (int) goodBarLength - (int) (PAD * 0.2));

            }

            g2.setPaint(Color.black);
            // g2.drawString(cate, (int) (xLevel + levelIncre * 0.2), h - (int) (PAD * 0.5));
            g2.drawString(cate, (int) xLevel, h - (int) (PAD * 0.5));
        }

        try {
            String fieldName = data.get(0).getFieldName();
            BufferedImage bi = new BufferedImage(IMAGE_W, IMAGE_H, BufferedImage.TYPE_INT_RGB);

            ImageIO.write(bi, "JPEG", new File(fieldName + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LinkedHashSet<String> findLevels() {
        LinkedHashSet<String> cateLevels = new LinkedHashSet<String>();
        for (CateFieldStat cateStat : data) {
            cateLevels.addAll(cateStat.getLevels());
        }
        return cateLevels;
    }

    private Double findYMax(List<CateFieldStat> data) {

        Double max = null;

        for (CateFieldStat stat : data) {
            if (max == null || stat.getMaxNormalizedCount() > max) {
                max = stat.getMaxNormalizedCount();
            }
        }

        return max;
    }
}
