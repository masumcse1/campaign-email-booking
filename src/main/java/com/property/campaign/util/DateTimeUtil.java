package com.property.campaign.util;


import com.property.campaign.common.dto.EmailDataDirectus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class DateTimeUtil  {

   public static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    public static String convertTimeStampToString(EmailDataDirectus emailDataDirectus , int campaignScheduleDayInterval){

        ZonedDateTime gmtDateTime = ZonedDateTime.now(ZoneId.of("GMT"));
        // Add 7 days to the current date
        ZonedDateTime sevenDaysLater = gmtDateTime.plusDays(campaignScheduleDayInterval);

        if(Objects.nonNull(emailDataDirectus.getWeekdays())){
            DayOfWeek dayOfWeek = sevenDaysLater.getDayOfWeek();
            String dayOfWeekString = dayOfWeek.toString().charAt(0)+dayOfWeek.toString().substring(1,3).toLowerCase();

            if (!emailDataDirectus.getWeekdays().contains(dayOfWeekString)){
                 return null;
            }
        }

        int hours=7;
        // Set the time to 07:00
        if(Objects.nonNull(emailDataDirectus.getTime())){
            String[] parts = emailDataDirectus.getTime().split(":");

            // Parse the hours as an integer
             hours = Integer.parseInt(parts[0]);
        }
        ZonedDateTime targetDateTime = sevenDaysLater.withHour(hours).withMinute(0).withSecond(0);
        //convert timezone to string
        return targetDateTime.toInstant().toString();
    }

    public static String convertCurrentTimeStampToString(){

        ZonedDateTime gmtDateTime = ZonedDateTime.now(ZoneId.of("GMT"));
        //convert timezone to string
        return gmtDateTime.toInstant().toString();
    }

    public static String dateToStringIndConvertor() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        Date twoYearsAgoDate = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(twoYearsAgoDate);
    }

    public static String dateToStringLast100DaysConvertor() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -100);
        Date twoYearsAgoDate = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(twoYearsAgoDate);
    }

    public static Timestamp convertStringToTimeStamp(String time){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");
        String timestampString = time.replace("T", " ").replace("Z", "").substring(0, 23);
        return Timestamp.valueOf(LocalDateTime.parse(timestampString, formatter));
    }

    public  static boolean compareDates(String date,String scheuledDate){
        return extractDate(date).isEqual(extractDate(scheuledDate));

    }

    public static LocalDate extractDate(String timestamp) {
        // Parse the timestamp string into an Instant object
        Instant instant = Instant.parse(timestamp);

        // Convert Instant to LocalDate in a specific time zone (e.g., UTC)
        ZoneId zoneId = ZoneId.of("UTC");
        LocalDate date = instant.atZone(zoneId).toLocalDate();

        return date;
    }

    public static Date getDateOfDaysBeforeNowIrrespectiveOfTime(Integer days) {
        long now = System.currentTimeMillis();
        long currentDay = now - now % TimeUnit.DAYS.toMillis(1);
        return new Date(currentDay - TimeUnit.DAYS.toMillis(days));
    }

    public static LocalDate getDate360DaysAgo() {
        return LocalDate.now().minusDays(360);
    }
}
