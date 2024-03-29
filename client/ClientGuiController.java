package com.javarush.task.task30.task3008.client;

public class ClientGuiController extends Client {
    private ClientGuiModel model;
    private ClientGuiView view;

    public ClientGuiController() {
        this.model = new ClientGuiModel();
        this.view = new ClientGuiView(this);
    }

    public static void main(String[] args) {
        ClientGuiController client = new ClientGuiController();
        client.run();
    }

    @Override
    protected SocketThread getSocketThread() {
        return new GuiSocketThread();
    }

    @Override
    protected int getServerPort() {
        return view.getServerPort();
    }

    @Override
    protected String getServerAddress() {
        return view.getServerAddress();
    }

    @Override
    protected String getUserName() {
        return view.getUserName();
    }

    @Override
    public void run() {
        getSocketThread().run();
    }

    public ClientGuiModel getModel() {
        return model;
    }

    public class GuiSocketThread extends SocketThread {
        @Override
        protected void processIncomingMessage(String message) {
            model.setNewMessage(message);
            view.refreshMessages();
        }

        @Override
        protected void informAboutAddingNewUser(String userName) {
            model.addUser(userName);
            view.refreshUsers();
        }

        @Override
        protected void informAboutDeletingNewUser(String userName) {
            model.deleteUser(userName);
            view.refreshUsers();
        }

        @Override
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            view.notifyConnectionStatusChanged(clientConnected);
        }
    }
}
