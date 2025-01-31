package com.hszg.demo.model;

public class PostsThread extends Thread {
    boolean allowedToPost = true;
    public void run(){
        allowedToPost = false;
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                System.out.println("Fehler beim Stoppen des Threads");
            }
        allowedToPost = true;
    }

    public boolean getAllowedToPost(){
        return allowedToPost;
    }
}
