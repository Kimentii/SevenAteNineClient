package com.yatty.sevenatenine.api.in_commands;

import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.yatty.sevenatenine.api.commands_with_data.Card;
import com.yatty.sevenatenine.api.commands_with_data.GameResult;

public class NewStateNotification implements InCommandInterface {
    public static final String TAG = NewStateNotification.class.getSimpleName();
    private int moveNumber;
    private String moveWinner;
    private boolean lastMove;
    private boolean stalemate;
    private Card nextCard;
    private GameResult gameResult;

    protected NewStateNotification(Parcel in) {
        moveNumber = in.readInt();
        moveWinner = in.readString();
        lastMove = in.readByte() != 0;
        stalemate = in.readByte() != 0;
        nextCard = in.readParcelable(Card.class.getClassLoader());
        gameResult = in.readParcelable(GameResult.class.getClassLoader());
    }

    public static final Creator<NewStateNotification> CREATOR = new Creator<NewStateNotification>() {
        @Override
        public NewStateNotification createFromParcel(Parcel in) {
            return new NewStateNotification(in);
        }

        @Override
        public NewStateNotification[] newArray(int size) {
            return new NewStateNotification[size];
        }
    };

    @Override
    public void doLogic(Handler handler) {
        Log.d(TAG, "NewStateNotification.doLogic");
        Message message = new Message();
        message.obj = this;
        handler.sendMessage(message);
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getMoveWinner() {
        return moveWinner;
    }

    public boolean isLastMove() {
        return lastMove;
    }

    public Card getNextCard() {
        return nextCard;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public boolean isStalemate() {
        return stalemate;
    }

    public void setStalemate(boolean stalemate) {
        this.stalemate = stalemate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(moveNumber);
        dest.writeString(moveWinner);
        dest.writeByte((byte) (lastMove ? 1 : 0));
        dest.writeByte((byte) (stalemate ? 1 : 0));
        dest.writeParcelable(nextCard, flags);
        dest.writeParcelable(gameResult, flags);
    }
}
