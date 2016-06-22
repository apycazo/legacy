package com.github.apycazo.voodoo.svc.x;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Andres Picazo
 */
public class TestLambda
{

    public static void runTest () {
        TestLambda tl = new TestLambda();
        tl.test();
    }

    public void test()
    {
        List<TestModel> list = new LinkedList<>();

        TestModel modelA = new TestModel();
        modelA.setId(1);
        modelA.setName("A");
        list.add(modelA);

        TestModel modelB = new TestModel();
        modelB.setId(2);
        modelB.setName("B");
        list.add(modelB);

        TestModel modelC = new TestModel();
        modelC.setId(3);
        modelC.setName("C");
        list.add(modelC);

        // now the actual test
        printModels(
                list,
                tm -> tm.getId() > 1
        );

        // correct form
        list.stream().filter(tm -> tm.getId() != 2).forEach(tm -> System.out.println("[" + tm.toString() + "]"));

        list.stream().forEach(tm -> {
            int x = tm.getId();
            System.out.println(x + " ==> " + tm.toString());
        });

        // Using mapper
        process(
                list,
                t -> t.getId() != 2,
                f -> f.getName(),
                c -> System.out.println(">" + c.toString())
        );

        operator(list, e -> {
            System.out.println("Element id is  : " + e.getId());
            System.out.println("Element name is: " + e.getName());
        });

        Function<Integer, Integer> mod = (x) -> {
            return x <= 0 ? -x : x;
        };

        System.out.println("Mod (-10) = " + mod.apply(-10));

    }

    private void printModels(List<TestModel> list, Predicate<TestModel> pred)
    {
        for (TestModel testModel : list) {
            if (pred.test(testModel)) {
                System.out.println(testModel.toString());
            }
        }
    }

    private void process (
            Iterable<TestModel> source,
            Predicate<TestModel> tester,
            Function<TestModel, String> mapper,
            Consumer<String> block)
    {
        for (TestModel p : source) {
            if (tester.test(p)) {
                String data = mapper.apply(p);
                block.accept(data);
            }
        }
    }

    private void operator (Iterable<TestModel> source, Consumer<TestModel> consumer)
    {
        source.forEach(tm -> consumer.accept(tm));
    }
}
