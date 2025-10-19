package com.intimace.data

import com.intimace.R
import com.intimace.model.Guide
import com.intimace.model.Order
import com.intimace.model.Product
import com.intimace.model.Timeline
import com.intimace.model.TimelineStep
import com.intimace.uistate.Account
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

val guides = listOf(
    Guide(R.drawable.guide_1_img, R.string.guide_1_title, R.string.guide_1_desc),
    Guide(R.drawable.guide_2_img, R.string.guide_2_title, R.string.guide_2_desc),
    Guide(R.drawable.guide_3_img, R.string.guide_3_title, R.string.guide_3_desc),
)

val products = listOf(
    Product(
        R.drawable.product_1_img,
        "Menstrual",
        "Menstrual Cup",
        "Makati",
        1499.99,
        60,
        "A reusable, eco-friendly alternative to pads and tampons that collects menstrual flow comfortably for up to 12 hours. Made of medical-grade silicone, it’s safe, leak-resistant, and ideal for active lifestyles.",
        "Mercury Drug"
    ),
    Product(
        R.drawable.product_2_img,
        "Testing",
        "Pregnancy Test Kit (2 Pack)",
        "Quezon City",
        637.75,
        70,
        "A reliable home test that provides quick and accurate results within minutes. The two-pack design allows for double-checking or future use.",
        "South Star Drug"
    ),
    Product(
        R.drawable.product_3_img,
        "Emergency",
        "Emergency Contraception",
        "Davao",
        1249.99,
        150,
        "A safe and effective pill designed to prevent pregnancy after unprotected sex or contraceptive failure. Best taken as soon as possible within 72 hours.",
        "Watson's"
    ),
    Product(
        R.drawable.product_4_img,
        "Pills",
        "Birth Control Pills (1 Month)",
        "Cebu",
        775.50,
        200,
        "A monthly pack of hormonal tablets that prevent ovulation and help regulate menstrual cycles. Taken daily, they provide reliable and convenient pregnancy prevention.",
        "MerryMart"
    ),
)

val timeFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)
val dateFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)

val input1 = "10:45 AM"
val time1: LocalTime = LocalTime.parse(input1, timeFormatter)

val input2 = "11:45 AM"
val time2: LocalTime = LocalTime.parse(input2, timeFormatter)

val input3 = "09:15 AM"
val time3: LocalTime = LocalTime.parse(input3, timeFormatter)

val input4 = "02:30 PM"
val time4: LocalTime = LocalTime.parse(input4, timeFormatter)

val inputDate1 = "October 5, 2025"
val date1: LocalDate = LocalDate.parse(inputDate1, dateFormatter)

val inputDate2 = "October 5, 2025"
val date2: LocalDate = LocalDate.parse(inputDate2, dateFormatter)

val inputDate3 = "October 6, 2025"
val date3: LocalDate = LocalDate.parse(inputDate3, dateFormatter)

val inputDate4 = "October 7, 2025"
val date4: LocalDate = LocalDate.parse(inputDate4, dateFormatter)
val sampleTimeline = Timeline(
    listOf(
        TimelineStep(
            "Order Placed",
            date1,
            time1,
        ),
        TimelineStep(
            "Order Confirmed",
            date2,
            time2,
        ),
        TimelineStep(
            "Order Shipped",
            date3,
            time3,
        ),
        TimelineStep(
            "Order Delivered",
            date4,
            time4,
        )
    )
)

var orders = listOf(
    Order(
        "ORD–2023-0001",
        date1,
        "Order Delivered",
        1137.74,
        249.99,
        1387.73,
        "123 Main Street, Makati City, Metro Manila, Philippines",
        "TRK123456789PH",
        sampleTimeline,
        listOf(products[0] to 1, products[1] to 1)
    )
)

var currentAccount = Account()

