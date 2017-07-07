package org.demo;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoAppServerApplicationTests {

	@Test
	public void contextLoads() {
		// intStream is lazy stream, pipeline wont flow unless terminal operation is invoked
        Stream<Integer> intStream = Stream.iterate(-1, i -> i + 1)
        .map(i -> i + 1)
        .peek(i -> System.out.println("Map: " + i))
        .limit(5);
        
  System.out.println();
  System.out.println();
  intStream.forEach(i -> {System.out.println("forEach Map: " + i);});
  Stream.iterate(0, i -> i + 1)
        .limit(5)
        .map(i -> i + 1)
        .peek(i -> System.out.println("Map: " + i))
        .forEach(i -> {});

	}
	
	@Test
	public void contextLoads2() {
		Stream<Integer> intStream = Stream.iterate(0, i -> i + 1)
        
        .flatMap(i -> Stream.of(i, i, i, i))
        .map(i -> i + 1)
        .peek(i -> System.out.println("Map: " + i))
        .limit(5);
        //.forEach(i -> {});
  System.out.println();
  System.out.println();
  Stream.iterate(0, i -> i + 1)
        .flatMap(i -> Stream.of(i, i, i, i))
        .limit(5)
        .map(i -> i + 1)
        .peek(i -> System.out.println("Map: " + i))
        .forEach(i -> {});
	}

}
