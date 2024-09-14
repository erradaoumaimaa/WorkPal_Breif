package org.example;

import service.MaWorkpalService;

public class Main {
    public static void main(String[] args) {
        try {
            MaWorkpalService maWorkpalService = MaWorkpalService.getInstance();
            maWorkpalService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
