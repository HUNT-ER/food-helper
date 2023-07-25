create table t_ingredients_categories
(
    ingredient_category_id   integer primary key generated by default as identity,
    ingredient_category_name varchar(100) not null unique,
    parent_category_id       integer references t_ingredients_categories (ingredient_category_id) on delete cascade
);

create table t_ingredients
(
    ingredient_id          integer primary key generated by default as identity,
    ingredient_name        varchar(100) not null,
    ingredient_category_id integer      references t_ingredients_categories (ingredient_category_id) on delete set null
);

create table t_recipes_categories
(
    recipe_category_id   integer primary key generated by default as identity,
    recipe_category_name varchar(100) not null unique,
    parent_category_id   integer references t_recipes_categories (recipe_category_id) on delete cascade
);

create table t_users
(
    user_id    integer primary key generated by default as identity,
    user_name  varchar(30) not null unique,
    password   varchar     not null,
    role       varchar     not null,
    created_at timestamp   not null
);

create table t_recipes
(
    recipe_id          integer primary key generated by default as identity,
    title              varchar(150) not null,
    description        varchar      not null,
    image_path         varchar      not null,
    recipe_category_id integer      not null references t_recipes_categories (recipe_category_id) on delete set null,
    user_id            integer      not null references t_users (user_id) on delete set null,
    created_at         timestamp    not null
);

create table t_recipes_ingredients
(
    recipe_id     integer references t_recipes (recipe_id) on delete cascade,
    ingredient_id integer references t_ingredients (ingredient_id) on delete cascade
);

delete
from t_ingredients_categories;
delete
from t_ingredients;
delete
from t_recipes_categories;
delete
from t_users;
delete
from t_recipes;
delete
from t_recipes_ingredients;

drop table t_recipes_ingredients;
drop table t_recipes;
drop table t_recipes_categories;
drop table t_ingredients;
drop table t_ingredients_categories;
drop table t_users;


insert into t_ingredients_categories(ingredient_category_name, parent_category_id)
values ('Meat', null),
       ('Vegetables', null),
       ('Beef', 1),
       ('Chicken', 1),
       ('Tomato', 2),
       ('Cucumber', 2);

insert into t_ingredients(ingredient_name, ingredient_category_id)
values ('Beef fillet', 3),
       ('Chicken breast', 4),
       ('Cherry tomato', 5),
       ('Long cucumber', 6);

insert into t_recipes_categories(recipe_category_name, parent_category_id)
values ('Soups', null),
       ('Salads', null),
       ('Cream-soups', 1),
       ('Regular-soups', 1),
       ('Vegetables salads', 2),
       ('Vegetables with meat salads', 2);

insert into t_users(user_name, password, role, created_at)
VALUES ('hunter', '0322', 'ADMIN', current_timestamp);

select *
from t_recipes;

select *
from t_recipes_ingredients;

select *
from t_recipes_categories;

select * from t_users;