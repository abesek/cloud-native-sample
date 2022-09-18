use db_allocation;

create table if not exists order_lines (
    id int auto_increment primary key,
    sku varchar(255),
    qty int not null,
    orderid varchar(255)
) ENGINE=INNODB;

create table if not exists products (
    sku varchar(255) primary key,
    version_number int not null default '0'
) ENGINE=INNODB;

create table if not exists batches (
    id int auto_increment primary key,
    reference varchar(255),
    sku varchar(255),
    purchased_quantity int not null,
    eta date,
    foreign key (sku) references products(sku)
) ENGINE=INNODB;

create table if not exists allocations (
    id int auto_increment primary key,
    orderline_id int,
    batch_id int,
    foreign key (orderline_id) references order_lines(id),
    foreign key (batch_id) references batches(id)
) ENGINE=INNODB;