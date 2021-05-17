# Java Spring Boot application
[![Spring Boot 2.4.4](https://img.shields.io/badge/spring%20boot-2.4.4-brightgreen)](https://spring.io/projects/spring-boot)
### About
[Simple Todo Application](https://todo--spring--boot.herokuapp.com/registration), where you can add/change/delete todos and monitor your activity as a user and where you can monitor activity of other users and add todos for them as admin. Made with using MVC pattern.

Model–view–controller (usually known as MVC) is a software design pattern commonly used for developing user interfaces which divides the related program logic into three interconnected elements.

### Model
The central component of the pattern. It is the application's dynamic data structure, independent of the user interface. It directly manages the data, logic and rules of the application.
```java
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
```
### View
Any representation of information such as a chart, diagram or table. Multiple views of the same information are possible, such as a bar chart for management and a tabular view for accountants.
```html
<div class="user__info">
   <p>${currentUser.getName()}</p>
   <p>${currentUser.getAge()}</p>
</div>
```
### Controller
Mediator between model and view.
```java
@GetMapping("/profile")
public String getPage(@RequestParam Integer userId, Model model) {
    model.addAttribute("currentUser", userService.getUser(userId));
    return "profile";
}
```
### DB structure 
![db-structure](https://sun9-34.userapi.com/impg/PGQqoLtW_pvtZZJ_rFb3aj4SMArISk1V_sfO9g/klT62yot9vs.jpg?size=925x558&quality=96&sign=b85765a007a9b831458dab3889978298&type=album)
### Stack of technologies 
* Spring Boot 2.4.4
* PostgreSQL
* HTML5, CSS3, JS (+ [chart.js](https://www.chartjs.org/))
* Figma ([layouts](https://www.figma.com/file/laW5XOXLXdCdLVt1Ma4DXa/Semester-Work))
