package animal.shelter.animalsshelter.controllers;

import animal.shelter.animalsshelter.config.Config;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Component
public class TelegramBotStart extends TelegramLongPollingBot {

    private final static String INFO_BUTTON = "INFO_BUTTON";
    private final static String NECESSARY = "NECESSARY_TO_GET_ANIMAL";
    private final static String SEND_REPORT = "SEND_REPORT";
    private final static String CALL_VOLUNTEER = "CALL_VOLUNTEER";
    private final static String URL_START_PHOTO = "src/main/resources/templates/msg6162958373-22385.jpg";


    final Config config;

    public TelegramBotStart(Config config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotKey();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        long id = update.getMessage().getChatId();
        if (update.hasMessage() && message.hasText()) {
            switch (message.getText()) {
                case "/hello":
                    sendBotMessage(id,"Привет - Это Asha)");
                    sendPhoto(id);
                    getBotStartUserMenu(id);
                    break;
                case "/start":
                    getBotStartUserMenu(id);
                    break;
                default:
                    sendBotMessage(id, "Вы ввели - " + message.getText());
                    System.out.println(message.getText());
                    log.info(id + " " + message.getText());
                    break;
            }
        }
    }

    private void sendBotMessage(long id, String name) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(id));
        sendMessage.setText(name);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void getBotStartUserMenu(long id) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(id));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton shelterInfoButton = new InlineKeyboardButton();
        shelterInfoButton.setText("Информация о приюте");
        shelterInfoButton.setCallbackData(INFO_BUTTON);
        InlineKeyboardButton necessary = new InlineKeyboardButton();
        necessary.setText("Хотите животное? Важно знать!");
        necessary.setCallbackData(NECESSARY);
        InlineKeyboardButton report = new InlineKeyboardButton();
        report.setText("Отправить отчёт о животном");
        report.setCallbackData(SEND_REPORT);
        InlineKeyboardButton call = new InlineKeyboardButton();
        call.setText("Вопрос к волонтёру");
        call.setCallbackData(CALL_VOLUNTEER);
        row.add(shelterInfoButton);
        row1.add(necessary);
        row2.add(report);
        row3.add(call);
        rows.add(row);
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        inlineKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setText("Главное меню");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }

    }

    private void sendPhoto(long id) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(id));
        sendPhoto.setPhoto(new InputFile(new File(URL_START_PHOTO)));
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}
