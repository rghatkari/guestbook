package org.rghatkari.guestbook.repository;

import org.rghatkari.guestbook.entities.GuestBook;
import org.rghatkari.guestbook.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GuestbookRepository extends CrudRepository<GuestBook, Integer> {

    @Query("SELECT g FROM GuestBook g")
    public List<GuestBook> fetchAllGuestBookMessages();

    @Query("UPDATE GuestBook g SET g.name=:name, g.message=:message, g.image=:image, g.updatedAt=:date WHERE g.id=:id")
    public void updateGuestBookMessage(@Param("id") Integer id, @Param("name") String name,
                                       @Param("message") String message,
                                       @Param("image") byte[] image,
                                       @Param("date") Date date);
}
