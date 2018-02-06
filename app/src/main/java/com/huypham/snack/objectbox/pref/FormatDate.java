package com.huypham.snack.objectbox.pref;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huyphamna on 07/02/2018.
 */

public class FormatDate {
    private Date date = new Date();
    public String formatDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(date);
}
