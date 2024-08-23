# java-filmorate

Изображение структуры базы данных:

![Структура базы данных](/readme_files/database-screenshot.png)

Подробное [описание](https://github.com/elizaveta-mokritskaya/java-filmorate/blob/main/readme_files/tables.dbml)
структуры в формате dbml. Там же указаны все возможные значения перечислений.

# Technology stack
* SpringBoot
* JDBC
* PostgreSQL
* Apache Maven

Примеры Sql запросов:

- получение всех фильмов по жанру
```
SELECT
    id,
    name
FROM film
RIGHT JOIN film_genre ON film.id = film_genre.film_id 
WHERE genre = DRAMA;
```
- получение топ-10 фильмов по количеству лайков
```
SELECT film.id,
       film.name
       COUNT(user_id) AS count_of_likes
FROM user_film
LEFT JOIN film ON user_film.film_id = film.id
GROUP BY film.id
ORDER BY count_of_likes DESC
LIMIT 10;
```
- получение всех входящих запросов дружбы для конкретного пользователя
```
SELECT u.id,
       u.name,
       u.login
FROM user_user AS uu
LEFT JOIN user AS u ON uu.first_user_id = u.id
WHERE uu.second_user_id = 1;
```
- получение количества фильмов по всем жанрам
```
SELECT genre,
       COUNT(*) AS count
FROM film_genre AS fg
LEFT JOIN film AS f ON fg.film_id = f.id
GROUP BY genre;
```
- получение всех пользователей, которым нравятся фильмы в жанре DRAMA
```
SELECT u.id,
       u.name,
       u.login
FROM user AS u
LEFT JOIN (SELECT DISTINCT user_id
           FROM (SELECT id
                 FROM film
                 RIGHT JOIN film_genre ON film.id = film_genre.film_id 
                 WHERE genre = DRAMA) AS drama_films
           RIGHT JOIN user_film AS uf ON drama_films.id = uf.film_id) AS users_liked_drama
ON u.id = users_liked_drama.user_id
ORDER BY u.id;
```