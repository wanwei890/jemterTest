package com.mytest.Learning;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author wanwei
 * @Date 2020/3/24 13:56
 * @Description
 * @Reviewer
 */
public class java8stream {
    @Test
    public void Foreach(){
        //你不鸟我，我也不鸟你
        List<String> list = Arrays.asList("you","don't","bird","me",",",
                "i","don't","bird","you");

        //方式一：jdk1.8之前的循环遍历方式
        for(String item:list){
            System.out.println(item);
        }

        //方式二：使用stream流
        // void forEach(Consumer<? super T> action)
        list.stream().forEach(item -> System.out.println(item));

        //方式三：使用stream流+lambda表达式
        list.stream().forEach(System.out::println);
    }

    public class User{
        private long id;
        private String name;
        private Integer age;

        public User(){}

        public User(long id, String name, Integer age){
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Integer getAge(){
            return this.age;
        }
    }

    @Test
    public void filter(){
        List<User> users = Arrays.asList(
                new User(1, "wanwei", 23),
                new User(2, "zhangsan", 25),
                new User(3, "lisi", 29)
        );
        users.stream().filter(user -> user.getAge()>24).forEach(user -> System.out.println(user.name));
    }

    @Test
    public void map(){
        List<String> list = Arrays.asList("djfh", "sdf", "sdfg");
        list.stream().map(item -> item.toUpperCase()).forEach(System.out::println);
    }

    @Test
    public void flatmap(){
        List<Integer> a = Arrays.asList(1,2,3);
        List<Integer> b = Arrays.asList(4,5,6);

        List<List<Integer>> collect = Stream.of(a,b).collect(Collectors.toList());
        System.out.println(collect);

        //将多个集合中的元素合并为一个集合
        List<Integer> mergelist = Stream.of(a,b).flatMap(list -> list.stream()).collect(Collectors.toList());
        System.out.println(mergelist);

        //通过Builder方式来构建
        Stream<Object> stream = Stream.builder().add("hello").add("hi").add("HI").build();
        stream.forEach(System.out::println);
    }

    @Test
    public void sorted(){
        List<String> list = Arrays.asList("c","b","e","a");
        list.stream().sorted((s1,s2) -> s1.compareTo(s2)).forEach(System.out::println);
    }

    @Test
    public void distinct(){
        Stream<String> stream = Stream.of("hello","hello","hi","hi","i");
        stream.distinct().forEach(System.out::println);
    }

    @Test
    public void count(){
        Stream<String> stream = Stream.of("known","is","known","nok","is","nok");
        long count = stream.count();
        System.out.println(count);
    }

    @Test
    public void concat(){
        List<String> list = Arrays.asList("a", "b");
        List<String> list2 = Arrays.asList("c", "d");
        Stream<String> concatStream = Stream.concat(list.stream(), list2.stream());
        concatStream.forEach(System.out::println);
    }

    @Test
    public void reduce(){
        Stream<String> stream = Stream.of("you", "give", "me", "stop");
        // Optional<T> reduce(BinaryOperator<T> accumulator);
        Optional<String> optional = stream.reduce((before, after) -> before + "," + after);
        optional.ifPresent(System.out::println);    // you,give,me,stop
    }

    @Test
    public void findFirst(){
        Stream<String> stream = Stream.of("you", "give", "me", "stop");
        String value = stream.findFirst().get();
        System.out.println(value);
    }

    @Test
    public void findAny(){
        Stream<String> stream = Stream.of("you", "give", "me", "stop");
        String value2 = stream.findAny().get();
        System.out.println(value2);
    }
}
