package com.video.service.repository;

import com.video.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
* JpaRepository에서 내부적으로 @Repository가 선언되어있기 때문에 따로 선언할 필요가 없습니다.
*
* <>의 구조는
* <매핑하고자 하는 객체를 주입, 해당 객체의 pk의 타입>
*/
public interface TestRepository extends JpaRepository<User, Integer> {
}
