package org.easyminer.statistics.histogramold;

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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public final class DualHistogram extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int PAD = 40;
    private String fieldName;

    private String source;

    private String histoDir;

    public final void setSource(String source) {
        this.source = source;
    }

    public final void setHistoDir(String histoDir) {
        this.histoDir = histoDir;
    }

    private List<HistoBar> goodHistoList = new ArrayList<HistoBar>();
    private List<HistoBar> badHistoList = new ArrayList<HistoBar>();

    public final String getFieldName() {
        return fieldName;
    }

    public final void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    private static final Color GOOD_COLOR = new Color(10, 150, 10);

    public void setHistoList(List<HistoBar> goodList, List<HistoBar> badList) {
        this.goodHistoList = goodList;
        this.badHistoList = badList;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        BufferedImage bi = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);

        Graphics2D ig2 = bi.createGraphics();

        ig2.setColor(Color.lightGray);
        ig2.fillRect(0, 0, 1200, 800);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ig2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(Color.lightGray);
        g2.fillRect(0, 0, w, h);

        g2.setColor(Color.black);
        ig2.setColor(Color.black);
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
        ig2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
        ig2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
        double xInc = (double) (w - 2 * PAD) / (goodHistoList.size());
        double scale = (h - 2 * PAD) / getMax(goodHistoList, badHistoList);

        // Mark data points.
        // g2.setPaint(Color.red);
        for (int i = 0; i < goodHistoList.size(); i++) {
            HistoBar goodBar = goodHistoList.get(i);
            HistoBar badBar = badHistoList.get(i);

            // String goodBarPct =
            // Double.valueOf(Math.round(goodBar.getCount() * 10000) / 100.0).toString() + " %";
            // String badBarPct =
            // Double.valueOf(Math.round(badBar.getCount() * 10000) / 100.0).toString() + " %";
            double x = PAD + i * xInc + xInc * 0.1;
            double bad_x = x + xInc * 0.4;
            int barWidth = (int) (xInc * 0.4);
            // double y = h - PAD - scale * bar.count;
            double goodBarLength = scale * goodBar.getCount();
            double badBarLength = scale * badBar.getCount();
            // System.out.println(bar.count + "  " + barLength);
            g2.setPaint(GOOD_COLOR);
            g2.fill(new Rectangle2D.Double(x, h - PAD - goodBarLength, barWidth, goodBarLength));
            // g2.setPaint(Color.black);
            // g2.drawString(goodBarPct, (int) x, h - PAD - (int) goodBarLength - (int) (PAD * 0.5));

            g2.setPaint(Color.red);
            g2.fill(new Rectangle2D.Double(bad_x, h - PAD - badBarLength, barWidth, badBarLength));
            // g2.setPaint(Color.black);
            // g2.drawString(badBarPct, (int) bad_x, h - PAD - (int) badBarLength - (int) (PAD * 0.5));

            // g2.setPaint(Color.black);
            // g2.drawString(goodBar.getName(), (int) x, h - (int) (PAD * 0.5));

            ig2.setPaint(GOOD_COLOR);
            ig2.fill(new Rectangle2D.Double(x, h - PAD - goodBarLength, barWidth, goodBarLength));
            // g2.setPaint(Color.black);
            // g2.drawString(goodBarPct, (int) x, h - PAD - (int) goodBarLength - (int) (PAD * 0.5));

            ig2.setPaint(Color.red);
            ig2.fill(new Rectangle2D.Double(bad_x, h - PAD - badBarLength, barWidth, badBarLength));
            // ig2.setPaint(Color.black);
            // ig2.drawString(badBarPct, (int) bad_x, h - PAD - (int) badBarLength - (int) (PAD * 0.5));
            //
            // ig2.setPaint(Color.black);
            // ig2.drawString(goodBar.getName(), (int) x, h - (int) (PAD * 0.5));

        }

        for (int i = 0; i < goodHistoList.size(); i++) {
            HistoBar goodBar = goodHistoList.get(i);
            HistoBar badBar = badHistoList.get(i);

            Double goodBarPctValue = Math.round(goodBar.getCount() * 10000) / 100.0;
            Double badBarPctValue = Math.round(badBar.getCount() * 10000) / 100.0;
            String goodBarPct = goodBarPctValue > 0.1 ? (goodBarPctValue.toString() + " %") : "";
            String badBarPct = badBarPctValue > 0.1 ? (badBarPctValue.toString() + " %") : "";

            double x = PAD + i * xInc;
            double bad_x = x + xInc * 0.5;

            // double y = h - PAD - scale * bar.count;
            double goodBarLength = scale * goodBar.getCount();
            double badBarLength = scale * badBar.getCount();
            g2.setPaint(Color.black);
            g2.drawString(goodBarPct, (int) x, h - PAD - (int) goodBarLength - (int) (PAD * 0.2));

            g2.setPaint(Color.black);
            g2.drawString(badBarPct, (int) bad_x, h - PAD - (int) badBarLength - (int) (PAD * 0.2));
            g2.setPaint(Color.darkGray);
            g2.fill(new Rectangle2D.Double(x + xInc, h - PAD - 4, 1, 5));
            g2.fill(new Rectangle2D.Double(x + (int) (xInc / 2), h - PAD - 2, 1, 3));

            g2.setPaint(Color.black);
            g2.drawString(goodBar.getName(), (int) (x + xInc / 4), h - (int) (PAD * 0.5));

            ig2.setPaint(Color.black);
            ig2.drawString(goodBarPct, (int) x, h - PAD - (int) goodBarLength - (int) (PAD * 0.2));

            ig2.setPaint(Color.black);
            ig2.drawString(badBarPct, (int) bad_x, h - PAD - (int) badBarLength - (int) (PAD * 0.2));
            ig2.setPaint(Color.darkGray);
            ig2.fill(new Rectangle2D.Double(x + xInc, h - PAD - 4, 1, 5));
            ig2.fill(new Rectangle2D.Double(x + (int) (xInc / 2), h - PAD - 2, 1, 3));

            ig2.setPaint(Color.black);
            ig2.drawString(goodBar.getName(), (int) (x + xInc / 4), h - (int) (PAD * 0.5));

        }

        // ig2.drawString(source, 500, h + 50);
        // g2.setPaint(Color.blue);
        // for (int i = 0; i < data.length; i++) {
        // g2.drawString("Meow" + i, PAD + i * 80, h - PAD);
        // g2.drawString(Double., PAD + i * 80, h - PAD);
        // }
        try {
            ImageIO.write(bi, "JPEG", new File(histoDir + source + "_" + fieldName + ".jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private double getMax(List<HistoBar> goodHistoList, List<HistoBar> badHistoList) {
        double max = -Integer.MAX_VALUE;
        for (int i = 0; i < goodHistoList.size(); i++) {
            if (goodHistoList.get(i).getCount() > max) {
                max = goodHistoList.get(i).getCount();
            }
            if (badHistoList.get(i).getCount() > max) {
                max = badHistoList.get(i).getCount();
            }
        }
        return max;
    }

    /*
     * public void setXArray(int[] a) { for (int i = 0; i < 10; i++) { data[i] = i * 5; } }
     */
}
