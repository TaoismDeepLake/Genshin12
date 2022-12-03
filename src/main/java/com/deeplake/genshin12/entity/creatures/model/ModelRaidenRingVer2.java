package com.deeplake.genshin12.entity.creatures.model;// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRaidenRingVer2 extends ModelBase {
	private final ModelRenderer ringCenter;
	private final ModelRenderer part1;
	private final ModelRenderer hex_main_1;
	private final ModelRenderer eye1;
	private final ModelRenderer ball2;
	private final ModelRenderer side2;
	private final ModelRenderer eyeLineOutDown_r1;
	private final ModelRenderer eyeLineOutUp_r1;
	private final ModelRenderer mirror2;
	private final ModelRenderer eyeLineOutDown_r2;
	private final ModelRenderer eyeLineOutUp_r2;
	private final ModelRenderer hexphase1;
	private final ModelRenderer hexphase2;
	private final ModelRenderer hexphase3;
	private final ModelRenderer hexphase4;
	private final ModelRenderer hexphase5;
	private final ModelRenderer hexphase6;
	private final ModelRenderer hex_main_2;
	private final ModelRenderer eye2;
	private final ModelRenderer ball3;
	private final ModelRenderer hexphase7;
	private final ModelRenderer hexphase8;
	private final ModelRenderer hexphase9;
	private final ModelRenderer hexphase10;
	private final ModelRenderer hexphase11;
	private final ModelRenderer hexphase12;
	private final ModelRenderer part2;
	private final ModelRenderer hex_main_3;
	private final ModelRenderer eye3;
	private final ModelRenderer ball4;
	private final ModelRenderer hexphase13;
	private final ModelRenderer hexphase14;
	private final ModelRenderer hexphase15;
	private final ModelRenderer hexphase16;
	private final ModelRenderer hexphase17;
	private final ModelRenderer hexphase18;
	private final ModelRenderer hex_main_4;
	private final ModelRenderer eye4;
	private final ModelRenderer ball5;
	private final ModelRenderer hexphase19;
	private final ModelRenderer hexphase20;
	private final ModelRenderer hexphase21;
	private final ModelRenderer hexphase22;
	private final ModelRenderer hexphase23;
	private final ModelRenderer hexphase24;
	private final ModelRenderer part3;
	private final ModelRenderer hex_main_5;
	private final ModelRenderer eye5;
	private final ModelRenderer ball6;
	private final ModelRenderer hexphase25;
	private final ModelRenderer hexphase26;
	private final ModelRenderer hexphase27;
	private final ModelRenderer hexphase28;
	private final ModelRenderer hexphase29;
	private final ModelRenderer hexphase30;
	private final ModelRenderer hex_main_6;
	private final ModelRenderer eye6;
	private final ModelRenderer ball7;
	private final ModelRenderer hexphase31;
	private final ModelRenderer hexphase32;
	private final ModelRenderer hexphase33;
	private final ModelRenderer hexphase34;
	private final ModelRenderer hexphase35;
	private final ModelRenderer hexphase36;
	private final ModelRenderer bb_main;

	public ModelRaidenRingVer2() {
		textureWidth = 64;
		textureHeight = 64;

		ringCenter = new ModelRenderer(this);
		ringCenter.setRotationPoint(0.0F, 0.0F, 0.0F);
		

		part1 = new ModelRenderer(this);
		part1.setRotationPoint(0.0F, 0.0F, 0.0F);
		ringCenter.addChild(part1);
		

		hex_main_1 = new ModelRenderer(this);
		hex_main_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		part1.addChild(hex_main_1);
		

		eye1 = new ModelRenderer(this);
		eye1.setRotationPoint(0.0F, -12.0005F, 0.0F);
		hex_main_1.addChild(eye1);
		

		ball2 = new ModelRenderer(this);
		ball2.setRotationPoint(0.0F, 0.0F, 0.0F);
		eye1.addChild(ball2);
		ball2.cubeList.add(new ModelBox(ball2, 19, 0, -1.0F, -0.9995F, -7.0F, 2, 2, 2, 0.0F, false));

		side2 = new ModelRenderer(this);
		side2.setRotationPoint(0.0F, 0.0F, 0.0F);
		eye1.addChild(side2);
		side2.cubeList.add(new ModelBox(side2, 0, 2, 3.0F, -0.4995F, -6.0F, 3, 1, 0, 0.0F, false));
		side2.cubeList.add(new ModelBox(side2, 0, 1, -1.5F, -2.2995F, -6.0F, 3, 1, 0, 0.0F, false));

		eyeLineOutDown_r1 = new ModelRenderer(this);
		eyeLineOutDown_r1.setRotationPoint(2.3601F, -0.8495F, 0.0F);
		side2.addChild(eyeLineOutDown_r1);
		setRotationAngle(eyeLineOutDown_r1, 0.0F, 0.0F, -0.7854F);
		eyeLineOutDown_r1.cubeList.add(new ModelBox(eyeLineOutDown_r1, 0, 0, -2.8F, 0.6F, -6.0F, 3, 1, 0, 0.0F, false));

		eyeLineOutUp_r1 = new ModelRenderer(this);
		eyeLineOutUp_r1.setRotationPoint(2.3601F, -0.8495F, 0.0F);
		side2.addChild(eyeLineOutUp_r1);
		setRotationAngle(eyeLineOutUp_r1, 0.0F, 0.0F, 0.7854F);
		eyeLineOutUp_r1.cubeList.add(new ModelBox(eyeLineOutUp_r1, 0, 0, -1.6F, -0.4F, -6.0F, 3, 1, 0, 0.0F, false));

		mirror2 = new ModelRenderer(this);
		mirror2.setRotationPoint(0.0F, 0.0005F, 0.0F);
		eye1.addChild(mirror2);
		setRotationAngle(mirror2, 0.0F, 0.0F, -3.1416F);
		mirror2.cubeList.add(new ModelBox(mirror2, 0, 2, 3.0F, -0.5F, -6.0F, 3, 1, 0, 0.0F, false));
		mirror2.cubeList.add(new ModelBox(mirror2, 0, 1, -1.5F, -2.3F, -6.0F, 3, 1, 0, 0.0F, false));

		eyeLineOutDown_r2 = new ModelRenderer(this);
		eyeLineOutDown_r2.setRotationPoint(2.3601F, -0.85F, 0.0F);
		mirror2.addChild(eyeLineOutDown_r2);
		setRotationAngle(eyeLineOutDown_r2, 0.0F, 0.0F, -0.7854F);
		eyeLineOutDown_r2.cubeList.add(new ModelBox(eyeLineOutDown_r2, 0, 0, -2.8F, 0.6F, -6.0F, 3, 1, 0, 0.0F, false));

		eyeLineOutUp_r2 = new ModelRenderer(this);
		eyeLineOutUp_r2.setRotationPoint(2.3601F, -0.85F, 0.0F);
		mirror2.addChild(eyeLineOutUp_r2);
		setRotationAngle(eyeLineOutUp_r2, 0.0F, 0.0F, 0.7854F);
		eyeLineOutUp_r2.cubeList.add(new ModelBox(eyeLineOutUp_r2, 0, 0, -1.6F, -0.4F, -6.0F, 3, 1, 0, 0.0F, false));

		hexphase1 = new ModelRenderer(this);
		hexphase1.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(hexphase1);
		setRotationAngle(hexphase1, 0.0F, 0.0F, -0.1571F);
		hexphase1.cubeList.add(new ModelBox(hexphase1, 60, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase1.cubeList.add(new ModelBox(hexphase1, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase2 = new ModelRenderer(this);
		hexphase2.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(hexphase2);
		setRotationAngle(hexphase2, 0.0F, 0.0F, -0.3142F);
		hexphase2.cubeList.add(new ModelBox(hexphase2, 56, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase2.cubeList.add(new ModelBox(hexphase2, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase3 = new ModelRenderer(this);
		hexphase3.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(hexphase3);
		setRotationAngle(hexphase3, 0.0F, 0.0F, -0.4712F);
		hexphase3.cubeList.add(new ModelBox(hexphase3, 52, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase3.cubeList.add(new ModelBox(hexphase3, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase4 = new ModelRenderer(this);
		hexphase4.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(hexphase4);
		setRotationAngle(hexphase4, 0.0F, 0.0F, -0.6283F);
		hexphase4.cubeList.add(new ModelBox(hexphase4, 60, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase4.cubeList.add(new ModelBox(hexphase4, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase5 = new ModelRenderer(this);
		hexphase5.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(hexphase5);
		setRotationAngle(hexphase5, 0.0F, 0.0F, -0.7854F);
		hexphase5.cubeList.add(new ModelBox(hexphase5, 56, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase5.cubeList.add(new ModelBox(hexphase5, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase6 = new ModelRenderer(this);
		hexphase6.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(hexphase6);
		setRotationAngle(hexphase6, 0.0F, 0.0F, -0.9425F);
		hexphase6.cubeList.add(new ModelBox(hexphase6, 52, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase6.cubeList.add(new ModelBox(hexphase6, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hex_main_2 = new ModelRenderer(this);
		hex_main_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		part1.addChild(hex_main_2);
		setRotationAngle(hex_main_2, 0.0F, 0.0F, -1.0472F);
		

		eye2 = new ModelRenderer(this);
		eye2.setRotationPoint(0.0F, -12.0005F, 0.0F);
		hex_main_2.addChild(eye2);
		

		ball3 = new ModelRenderer(this);
		ball3.setRotationPoint(0.0F, 0.0F, 0.0F);
		eye2.addChild(ball3);
		ball3.cubeList.add(new ModelBox(ball3, 19, 0, -1.0F, -0.9995F, -7.0F, 2, 2, 2, 0.0F, false));

		hexphase7 = new ModelRenderer(this);
		hexphase7.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(hexphase7);
		setRotationAngle(hexphase7, 0.0F, 0.0F, -0.1571F);
		hexphase7.cubeList.add(new ModelBox(hexphase7, 52, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase7.cubeList.add(new ModelBox(hexphase7, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase8 = new ModelRenderer(this);
		hexphase8.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(hexphase8);
		setRotationAngle(hexphase8, 0.0F, 0.0F, -0.3142F);
		hexphase8.cubeList.add(new ModelBox(hexphase8, 56, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase8.cubeList.add(new ModelBox(hexphase8, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase9 = new ModelRenderer(this);
		hexphase9.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(hexphase9);
		setRotationAngle(hexphase9, 0.0F, 0.0F, -0.4712F);
		hexphase9.cubeList.add(new ModelBox(hexphase9, 60, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase9.cubeList.add(new ModelBox(hexphase9, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase10 = new ModelRenderer(this);
		hexphase10.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(hexphase10);
		setRotationAngle(hexphase10, 0.0F, 0.0F, -0.6283F);
		hexphase10.cubeList.add(new ModelBox(hexphase10, 52, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase10.cubeList.add(new ModelBox(hexphase10, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase11 = new ModelRenderer(this);
		hexphase11.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(hexphase11);
		setRotationAngle(hexphase11, 0.0F, 0.0F, -0.7854F);
		hexphase11.cubeList.add(new ModelBox(hexphase11, 56, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase11.cubeList.add(new ModelBox(hexphase11, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase12 = new ModelRenderer(this);
		hexphase12.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(hexphase12);
		setRotationAngle(hexphase12, 0.0F, 0.0F, -0.9425F);
		hexphase12.cubeList.add(new ModelBox(hexphase12, 60, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase12.cubeList.add(new ModelBox(hexphase12, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		part2 = new ModelRenderer(this);
		part2.setRotationPoint(0.0F, 0.0F, 0.0F);
		ringCenter.addChild(part2);
		setRotationAngle(part2, 0.0F, 0.0F, -2.0944F);
		

		hex_main_3 = new ModelRenderer(this);
		hex_main_3.setRotationPoint(0.0F, 0.0F, 0.0F);
		part2.addChild(hex_main_3);
		

		eye3 = new ModelRenderer(this);
		eye3.setRotationPoint(0.0F, -12.0005F, 0.0F);
		hex_main_3.addChild(eye3);
		

		ball4 = new ModelRenderer(this);
		ball4.setRotationPoint(0.0F, 0.0F, 0.0F);
		eye3.addChild(ball4);
		ball4.cubeList.add(new ModelBox(ball4, 19, 0, -1.0F, -0.9995F, -7.0F, 2, 2, 2, 0.0F, false));

		hexphase13 = new ModelRenderer(this);
		hexphase13.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(hexphase13);
		setRotationAngle(hexphase13, 0.0F, 0.0F, -0.1571F);
		hexphase13.cubeList.add(new ModelBox(hexphase13, 60, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase13.cubeList.add(new ModelBox(hexphase13, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase14 = new ModelRenderer(this);
		hexphase14.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(hexphase14);
		setRotationAngle(hexphase14, 0.0F, 0.0F, -0.3142F);
		hexphase14.cubeList.add(new ModelBox(hexphase14, 56, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase14.cubeList.add(new ModelBox(hexphase14, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase15 = new ModelRenderer(this);
		hexphase15.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(hexphase15);
		setRotationAngle(hexphase15, 0.0F, 0.0F, -0.4712F);
		hexphase15.cubeList.add(new ModelBox(hexphase15, 52, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase15.cubeList.add(new ModelBox(hexphase15, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase16 = new ModelRenderer(this);
		hexphase16.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(hexphase16);
		setRotationAngle(hexphase16, 0.0F, 0.0F, -0.6283F);
		hexphase16.cubeList.add(new ModelBox(hexphase16, 60, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase16.cubeList.add(new ModelBox(hexphase16, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase17 = new ModelRenderer(this);
		hexphase17.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(hexphase17);
		setRotationAngle(hexphase17, 0.0F, 0.0F, -0.7854F);
		hexphase17.cubeList.add(new ModelBox(hexphase17, 56, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase17.cubeList.add(new ModelBox(hexphase17, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase18 = new ModelRenderer(this);
		hexphase18.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(hexphase18);
		setRotationAngle(hexphase18, 0.0F, 0.0F, -0.9425F);
		hexphase18.cubeList.add(new ModelBox(hexphase18, 52, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase18.cubeList.add(new ModelBox(hexphase18, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hex_main_4 = new ModelRenderer(this);
		hex_main_4.setRotationPoint(0.0F, 0.0F, 0.0F);
		part2.addChild(hex_main_4);
		setRotationAngle(hex_main_4, 0.0F, 0.0F, -1.0472F);
		

		eye4 = new ModelRenderer(this);
		eye4.setRotationPoint(0.0F, -12.0005F, 0.0F);
		hex_main_4.addChild(eye4);
		

		ball5 = new ModelRenderer(this);
		ball5.setRotationPoint(0.0F, 0.0F, 0.0F);
		eye4.addChild(ball5);
		ball5.cubeList.add(new ModelBox(ball5, 19, 0, -1.0F, -0.9995F, -7.0F, 2, 2, 2, 0.0F, false));

		hexphase19 = new ModelRenderer(this);
		hexphase19.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(hexphase19);
		setRotationAngle(hexphase19, 0.0F, 0.0F, -0.1571F);
		hexphase19.cubeList.add(new ModelBox(hexphase19, 52, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase19.cubeList.add(new ModelBox(hexphase19, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase20 = new ModelRenderer(this);
		hexphase20.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(hexphase20);
		setRotationAngle(hexphase20, 0.0F, 0.0F, -0.3142F);
		hexphase20.cubeList.add(new ModelBox(hexphase20, 56, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase20.cubeList.add(new ModelBox(hexphase20, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase21 = new ModelRenderer(this);
		hexphase21.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(hexphase21);
		setRotationAngle(hexphase21, 0.0F, 0.0F, -0.4712F);
		hexphase21.cubeList.add(new ModelBox(hexphase21, 60, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase21.cubeList.add(new ModelBox(hexphase21, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase22 = new ModelRenderer(this);
		hexphase22.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(hexphase22);
		setRotationAngle(hexphase22, 0.0F, 0.0F, -0.6283F);
		hexphase22.cubeList.add(new ModelBox(hexphase22, 52, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase22.cubeList.add(new ModelBox(hexphase22, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase23 = new ModelRenderer(this);
		hexphase23.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(hexphase23);
		setRotationAngle(hexphase23, 0.0F, 0.0F, -0.7854F);
		hexphase23.cubeList.add(new ModelBox(hexphase23, 56, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase23.cubeList.add(new ModelBox(hexphase23, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase24 = new ModelRenderer(this);
		hexphase24.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(hexphase24);
		setRotationAngle(hexphase24, 0.0F, 0.0F, -0.9425F);
		hexphase24.cubeList.add(new ModelBox(hexphase24, 60, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase24.cubeList.add(new ModelBox(hexphase24, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		part3 = new ModelRenderer(this);
		part3.setRotationPoint(0.0F, 0.0F, 0.0F);
		ringCenter.addChild(part3);
		setRotationAngle(part3, 0.0F, 0.0F, 2.0944F);
		

		hex_main_5 = new ModelRenderer(this);
		hex_main_5.setRotationPoint(0.0F, 0.0F, 0.0F);
		part3.addChild(hex_main_5);
		

		eye5 = new ModelRenderer(this);
		eye5.setRotationPoint(0.0F, -12.0005F, 0.0F);
		hex_main_5.addChild(eye5);
		

		ball6 = new ModelRenderer(this);
		ball6.setRotationPoint(0.0F, 0.0F, 0.0F);
		eye5.addChild(ball6);
		ball6.cubeList.add(new ModelBox(ball6, 19, 0, -1.0F, -0.9995F, -7.0F, 2, 2, 2, 0.0F, false));

		hexphase25 = new ModelRenderer(this);
		hexphase25.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(hexphase25);
		setRotationAngle(hexphase25, 0.0F, 0.0F, -0.1571F);
		hexphase25.cubeList.add(new ModelBox(hexphase25, 60, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase25.cubeList.add(new ModelBox(hexphase25, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase26 = new ModelRenderer(this);
		hexphase26.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(hexphase26);
		setRotationAngle(hexphase26, 0.0F, 0.0F, -0.3142F);
		hexphase26.cubeList.add(new ModelBox(hexphase26, 56, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase26.cubeList.add(new ModelBox(hexphase26, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase27 = new ModelRenderer(this);
		hexphase27.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(hexphase27);
		setRotationAngle(hexphase27, 0.0F, 0.0F, -0.4712F);
		hexphase27.cubeList.add(new ModelBox(hexphase27, 52, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase27.cubeList.add(new ModelBox(hexphase27, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase28 = new ModelRenderer(this);
		hexphase28.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(hexphase28);
		setRotationAngle(hexphase28, 0.0F, 0.0F, -0.6283F);
		hexphase28.cubeList.add(new ModelBox(hexphase28, 60, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase28.cubeList.add(new ModelBox(hexphase28, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase29 = new ModelRenderer(this);
		hexphase29.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(hexphase29);
		setRotationAngle(hexphase29, 0.0F, 0.0F, -0.7854F);
		hexphase29.cubeList.add(new ModelBox(hexphase29, 56, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase29.cubeList.add(new ModelBox(hexphase29, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase30 = new ModelRenderer(this);
		hexphase30.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(hexphase30);
		setRotationAngle(hexphase30, 0.0F, 0.0F, -0.9425F);
		hexphase30.cubeList.add(new ModelBox(hexphase30, 52, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase30.cubeList.add(new ModelBox(hexphase30, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hex_main_6 = new ModelRenderer(this);
		hex_main_6.setRotationPoint(0.0F, 0.0F, 0.0F);
		part3.addChild(hex_main_6);
		setRotationAngle(hex_main_6, 0.0F, 0.0F, -1.0472F);
		

		eye6 = new ModelRenderer(this);
		eye6.setRotationPoint(0.0F, -12.0005F, 0.0F);
		hex_main_6.addChild(eye6);
		

		ball7 = new ModelRenderer(this);
		ball7.setRotationPoint(0.0F, 0.0F, 0.0F);
		eye6.addChild(ball7);
		ball7.cubeList.add(new ModelBox(ball7, 19, 0, -1.0F, -0.9995F, -7.0F, 2, 2, 2, 0.0F, false));

		hexphase31 = new ModelRenderer(this);
		hexphase31.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(hexphase31);
		setRotationAngle(hexphase31, 0.0F, 0.0F, -0.1571F);
		hexphase31.cubeList.add(new ModelBox(hexphase31, 52, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase31.cubeList.add(new ModelBox(hexphase31, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase32 = new ModelRenderer(this);
		hexphase32.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(hexphase32);
		setRotationAngle(hexphase32, 0.0F, 0.0F, -0.3142F);
		hexphase32.cubeList.add(new ModelBox(hexphase32, 56, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase32.cubeList.add(new ModelBox(hexphase32, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase33 = new ModelRenderer(this);
		hexphase33.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(hexphase33);
		setRotationAngle(hexphase33, 0.0F, 0.0F, -0.4712F);
		hexphase33.cubeList.add(new ModelBox(hexphase33, 60, 3, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase33.cubeList.add(new ModelBox(hexphase33, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase34 = new ModelRenderer(this);
		hexphase34.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(hexphase34);
		setRotationAngle(hexphase34, 0.0F, 0.0F, -0.6283F);
		hexphase34.cubeList.add(new ModelBox(hexphase34, 52, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase34.cubeList.add(new ModelBox(hexphase34, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase35 = new ModelRenderer(this);
		hexphase35.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(hexphase35);
		setRotationAngle(hexphase35, 0.0F, 0.0F, -0.7854F);
		hexphase35.cubeList.add(new ModelBox(hexphase35, 56, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase35.cubeList.add(new ModelBox(hexphase35, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		hexphase36 = new ModelRenderer(this);
		hexphase36.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(hexphase36);
		setRotationAngle(hexphase36, 0.0F, 0.0F, -0.9425F);
		hexphase36.cubeList.add(new ModelBox(hexphase36, 60, 0, -0.5F, -12.5F, -6.5F, 1, 1, 1, 0.0F, false));
		hexphase36.cubeList.add(new ModelBox(hexphase36, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -3.0F, -28.0F, -4.0F, 6, 28, 7, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		ringCenter.render(f5);
		bb_main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}