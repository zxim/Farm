package com.Hanium.Farm.Farm.Service;

import com.Hanium.Farm.Farm.Domain.Fruit;
import com.Hanium.Farm.Farm.Domain.RecommendFruit;
import com.Hanium.Farm.Farm.Repository.FruitRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class FarmApplicationTests {

	@Autowired
	FruitRepositoryInterface fruitRepository;

	@Test
	public void FuitTest(){
		// given
		String name = "사과";
		Fruit info = fruitRepository.getFruitInfo(name);

		assertThat(info.getFruit_name()).isEqualTo("사과");
		assertThat(info.getCalories()).isEqualTo("57.0");
	}

	@Test
	public void FruitNameTest(){
		ArrayList<String> list = fruitRepository.getFruitNames();

		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}

	@Test
	public void RecommendTest(){
		String[] nutritions = {"비타민 A", "비타민 C", "비타민 E"};
		ArrayList<RecommendFruit> result = fruitRepository.getRecommendFruit(nutritions);
		Iterator<RecommendFruit> it = result.iterator();
		while(it.hasNext()){
			RecommendFruit temp = it.next();
			System.out.println(temp.getNutrition_name() + temp.getFruit_name());
		}
	}
}
