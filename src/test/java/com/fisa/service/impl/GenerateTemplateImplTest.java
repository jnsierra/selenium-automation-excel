package com.fisa.service.impl;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenerateTemplateImplTest {
/**
    private MustacheFactory mf;
    private Mustache m;

    @Before
    public void init(){
        this.mf = new DefaultMustacheFactory();

    }

    @Test
    public void generarTemplateSingleTest(){
        this.m = mf.compile("templates/basic.mustache");
        StringWriter writer = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        context.put("message", "esta es una prueba");
        m.execute(writer, context);
        String valor = writer.toString();
        System.out.println(valor);
        Assert.isTrue(Boolean.TRUE);
    }

    @Test
    public void generateTemplateOneItemTest(){
        this.m = mf.compile("templates/item.mustache");
        StringWriter writer = new StringWriter();
        StepEntity step = new StepEntity();
        step.setIterator(1);
        step.setDescription("aaaa");

        Map<String, Object> context = new HashMap<>();
        context.put("stepEntity", step);
        m.execute(writer, context);
        String valor = writer.toString();
        System.out.println(valor);
        Assert.isTrue(Boolean.TRUE);
    }

    @Test
    public void generateTemplateListItemTest(){
        this.m = mf.compile("templates/list.mustache");
        StringWriter writer = new StringWriter();

        StepEntity step = new StepEntity();
        step.setIterator(1);
        step.setDescription("aaaa");

        StepEntity stepDos = new StepEntity();
        stepDos.setIterator(2);
        stepDos.setDescription("bbbb");

        List<StepEntity> list = new ArrayList<>();
        list.add(step);
        list.add(stepDos);

        Map<String, Object> context = new HashMap<>();
        context.put("steps", list);
        m.execute(writer, context);
        String valor = writer.toString();
        System.out.println(valor);
        Assert.isTrue(Boolean.TRUE);
    }

    @Test
    public void generateTemplateExamplePdf(){
        this.m = mf.compile("templates/example.mustache");
        StringWriter writer = new StringWriter();
        StepEntity step = new StepEntity();
        step.setIterator(1);
        step.setDescription("aaaa");
        Map<String, Object> context = new HashMap<>();
        context.put("steps", step);
        m.execute(writer, context);
        String valor = writer.toString();

        File inputHTML = new File(valor);
        Document document = new Document();

        document.open();
        //XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(inputHTML));
        document.close();
        System.out.println(valor);


    }**/

}