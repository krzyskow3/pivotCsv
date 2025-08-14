create table settlement.sale_records
(
    origin_order_id     bigint            not null,
    record_idx          integer           not null,
    order_id            bigint            not null,
    item_id             bigint            not null,
    operation_idx       integer           not null,
    operation_type      varchar(255)      not null,
    operation_date      timestamp         not null,
    carrier_code        integer           not null,
    ticket_id           bigint            not null,
    ticket_number       varchar(255)      not null,
    item_type           varchar(255)      not null,
    base_price          integer           not null,
    discount_code       integer           not null,
    discount_percentage integer           not null,
    sale_brutto         integer           not null,
    sale_netto          integer           not null,
    return_type         varchar(255),
    return_brutto       integer           not null,
    return_netto        integer           not null,
    compensation        integer           not null,
    refund_id           bigint            not null,
    diff_sale_netto     integer default 0 not null,
    diff_return_netto   integer default 0 not null,
    diff_compensation   integer default 0 not null,
    tpay_matching_id    bigint,
    offer_code          integer,
    comment             text,
    op_day              date,
    primary key (origin_order_id, record_idx)
);



