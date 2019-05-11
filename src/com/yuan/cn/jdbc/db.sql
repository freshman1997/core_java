drop table if exits `video_details`;
create table `video_details`
(
    `id` int(11) not null auto_increment,
    `caption` varchar(50) not null default "",
    `img` longblob not null,
    `title` varchar(150) not null,
    primary key (`id`)
)engine=InnoDB DEFAULT CHARSET=utf8;