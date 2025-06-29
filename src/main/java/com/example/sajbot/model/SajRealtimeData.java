package com.example.sajbot.model;

import java.time.LocalDateTime;

public class SajRealtimeData {
    private int mpvmode;
    private String mpvstatus;
    private double pv1volt;
    private double pv1curr;
    private double pv1power;
    private double pv2volt;
    private double pv2curr;
    private double pv2power;
    private double pv3volt;
    private double pv3curr;
    private double pv3power;
    private double busvolt;
    private double invtempc;
    private int gfci;
    private int power;
    private int qpower;
    private double pf;
    private double l1volt;
    private double l1curr;
    private double l1freq;
    private int l1dci;
    private int l1power;
    private double l1pf;
    private double l2volt;
    private double l2curr;
    private double l2freq;
    private int l2dci;
    private int l2power;
    private double l2pf;
    private double l3volt;
    private double l3curr;
    private double l3freq;
    private int l3dci;
    private int l3power;
    private double l3pf;
    private int iso1;
    private int iso2;
    private int iso3;
    private int iso4;
    private double todayenergy;
    private double monthenergy;
    private double yearenergy;
    private double totalenergy;
    private double todayhour;
    private double totalhour;
    private int errorcount;
    private String datetime;

    public static SajRealtimeData parse(int[] r) {
        SajRealtimeData d = new SajRealtimeData();
        d.mpvmode = r[0];
        d.mpvstatus = switch (r[0]) {
            case 0 -> "Not Connected";
            case 1 -> "Waiting";
            case 2 -> "Normal";
            case 3 -> "Error";
            case 4 -> "Upgrading";
            default -> "Unknown";
        };
        d.pv1volt = round1(r[7] * 0.1);
        d.pv1curr = round2(r[8] * 0.01);
        d.pv1power = r[9];
        d.pv2volt = round1(r[10] * 0.1);
        d.pv2curr = round2(r[11] * 0.01);
        d.pv2power = r[12];
        d.pv3volt = round1(r[13] * 0.1);
        d.pv3curr = round2(r[14] * 0.01);
        d.pv3power = r[15];
        d.busvolt = round1(r[16] * 0.1);
        d.invtempc = round1(r[17] * 0.1);
        d.gfci = toSigned(r[18]);
        d.power = r[19];
        d.qpower = toSigned(r[20]);
        d.pf = round3(toSigned(r[21]) * 0.001);
        d.l1volt = round1(r[22] * 0.1);
        d.l1curr = round2(r[23] * 0.01);
        d.l1freq = round2(r[24] * 0.01);
        d.l1dci = toSigned(r[25]);
        d.l1power = r[26];
        d.l1pf = round3(toSigned(r[27]) * 0.001);
        d.l2volt = round1(r[28] * 0.1);
        d.l2curr = round2(r[29] * 0.01);
        d.l2freq = round2(r[30] * 0.01);
        d.l2dci = toSigned(r[31]);
        d.l2power = r[32];
        d.l2pf = round3(toSigned(r[33]) * 0.001);
        d.l3volt = round1(r[34] * 0.1);
        d.l3curr = round2(r[35] * 0.01);
        d.l3freq = round2(r[36] * 0.01);
        d.l3dci = toSigned(r[37]);
        d.l3power = r[38];
        d.l3pf = round3(toSigned(r[39]) * 0.001);
        d.iso1 = r[40];
        d.iso2 = r[41];
        d.iso3 = r[42];
        d.iso4 = r[43];
        d.todayenergy = round2(r[44] * 0.01);
        d.monthenergy = round2(((r[45] << 16) | r[46]) * 0.01);
        d.yearenergy = round2(((r[47] << 16) | r[48]) * 0.01);
        d.totalenergy = round2(((r[49] << 16) | r[50]) * 0.01);
        d.todayhour = round1(r[51] * 0.1);
        d.totalhour = round1(((r[52] << 16) | r[53]) * 0.1);
        d.errorcount = r[54];
        d.datetime = parseDateTime(new int[]{r[55], r[56], r[57], r[58]});
        return d;
    }

    private static int toSigned(int v) {
        return v >= 0x8000 ? v - 0x10000 : v;
    }

    private static double round1(double v) { return Math.round(v * 10.0) / 10.0; }
    private static double round2(double v) { return Math.round(v * 100.0) / 100.0; }
    private static double round3(double v) { return Math.round(v * 1000.0) / 1000.0; }

    private static String parseDateTime(int[] regs) {
        int year = regs[0];
        int month = regs[1] >> 8;
        int day = regs[1] & 0xFF;
        int hour = regs[2] >> 8;
        int minute = regs[2] & 0xFF;
        int second = regs[3] >> 8;
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }

    // getters omitted for brevity
    public int getMpvmode() { return mpvmode; }
    public String getMpvstatus() { return mpvstatus; }
    public double getPv1volt() { return pv1volt; }
    public double getPv1curr() { return pv1curr; }
    public double getPv1power() { return pv1power; }
    public double getPv2volt() { return pv2volt; }
    public double getPv2curr() { return pv2curr; }
    public double getPv2power() { return pv2power; }
    public double getPv3volt() { return pv3volt; }
    public double getPv3curr() { return pv3curr; }
    public double getPv3power() { return pv3power; }
    public double getBusvolt() { return busvolt; }
    public double getInvtempc() { return invtempc; }
    public int getGfci() { return gfci; }
    public int getPower() { return power; }
    public int getQpower() { return qpower; }
    public double getPf() { return pf; }
    public double getL1volt() { return l1volt; }
    public double getL1curr() { return l1curr; }
    public double getL1freq() { return l1freq; }
    public int getL1dci() { return l1dci; }
    public int getL1power() { return l1power; }
    public double getL1pf() { return l1pf; }
    public double getL2volt() { return l2volt; }
    public double getL2curr() { return l2curr; }
    public double getL2freq() { return l2freq; }
    public int getL2dci() { return l2dci; }
    public int getL2power() { return l2power; }
    public double getL2pf() { return l2pf; }
    public double getL3volt() { return l3volt; }
    public double getL3curr() { return l3curr; }
    public double getL3freq() { return l3freq; }
    public int getL3dci() { return l3dci; }
    public int getL3power() { return l3power; }
    public double getL3pf() { return l3pf; }
    public int getIso1() { return iso1; }
    public int getIso2() { return iso2; }
    public int getIso3() { return iso3; }
    public int getIso4() { return iso4; }
    public double getTodayenergy() { return todayenergy; }
    public double getMonthenergy() { return monthenergy; }
    public double getYearenergy() { return yearenergy; }
    public double getTotalenergy() { return totalenergy; }
    public double getTodayhour() { return todayhour; }
    public double getTotalhour() { return totalhour; }
    public int getErrorcount() { return errorcount; }
    public String getDatetime() { return datetime; }
}
