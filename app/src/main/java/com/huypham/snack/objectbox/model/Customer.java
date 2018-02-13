package com.huypham.snack.objectbox.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Snack project_object box
 * Created by HuyPhamNA on 07/02/2018.
 */

@Entity
public class Customer {
    @Id
    public long id;

    @Backlink
    public ToMany<Order> orders;
}
