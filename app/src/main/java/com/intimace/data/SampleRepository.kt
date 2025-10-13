package com.intimace.data

import com.intimace.R
import com.intimace.model.Guide
import com.intimace.model.Order
import com.intimace.model.Product

val guides = listOf(
    Guide(R.drawable.guide_1_img, R.string.guide_1_title, R.string.guide_1_desc),
    Guide(R.drawable.guide_2_img, R.string.guide_2_title, R.string.guide_2_desc),
    Guide(R.drawable.guide_3_img, R.string.guide_3_title, R.string.guide_3_desc),
)

val products = listOf(
    Product(
        R.drawable.product_1_img,
        R.string.product_1_type,
        R.string.product_1_name,
        R.string.product_1_location,
        R.string.product_1_price,
        R.string.product_1_desc,
        R.string.product_1_bch
    ),
    Product(
        R.drawable.product_2_img,
        R.string.product_2_type,
        R.string.product_2_name,
        R.string.product_2_location,
        R.string.product_2_price,
        R.string.product_2_desc,
        R.string.product_2_bch
    ),
    Product(
        R.drawable.product_3_img,
        R.string.product_3_type,
        R.string.product_3_name,
        R.string.product_3_location,
        R.string.product_3_price,
        R.string.product_3_desc,
        R.string.product_3_bch
    ),
    Product(
        R.drawable.product_4_img,
        R.string.product_4_type,
        R.string.product_4_name,
        R.string.product_4_location,
        R.string.product_4_price,
        R.string.product_4_desc,
        R.string.product_4_bch
    ),
)

val order = Order(
    R.string.order_1_id,
    R.string.order_1_placement_date,
    R.string.order_1_status,
    R.string.order_1_subtotal,
    R.string.order_1_shipping,
    R.string.order_1_total,
    R.string.order_1_address,
    R.string.order_1_tracking_number,
    listOf(products[0], products[1])
)

