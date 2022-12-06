package com.deeplake.genshin12.entity.creatures.model;// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.deeplake.genshin12.init.ModConfig;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRaidenRingVer2 extends ModelBase {
	ModelRenderer[] nodeListLit;
	ModelRenderer[] nodeListUnlit;
	ModelRenderer[] eyeList;
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
	private final ModelRenderer enable1;
	private final ModelRenderer enable2;
	private final ModelRenderer enable3;
	private final ModelRenderer enable4;
	private final ModelRenderer enable5;
	private final ModelRenderer enable6;
	private final ModelRenderer disable1;
	private final ModelRenderer disable2;
	private final ModelRenderer disable3;
	private final ModelRenderer disable4;
	private final ModelRenderer disable5;
	private final ModelRenderer disable6;
	private final ModelRenderer hex_main_2;
	private final ModelRenderer eye2;
	private final ModelRenderer ball3;
	private final ModelRenderer enable7;
	private final ModelRenderer enable8;
	private final ModelRenderer enable9;
	private final ModelRenderer enable10;
	private final ModelRenderer enable11;
	private final ModelRenderer enable12;
	private final ModelRenderer disable7;
	private final ModelRenderer disable8;
	private final ModelRenderer disable9;
	private final ModelRenderer disable10;
	private final ModelRenderer disable11;
	private final ModelRenderer disable12;
	private final ModelRenderer part2;
	private final ModelRenderer hex_main_3;
	private final ModelRenderer eye3;
	private final ModelRenderer ball4;
	private final ModelRenderer enable13;
	private final ModelRenderer enable14;
	private final ModelRenderer enable15;
	private final ModelRenderer enable16;
	private final ModelRenderer enable17;
	private final ModelRenderer enable18;
	private final ModelRenderer disable13;
	private final ModelRenderer disable14;
	private final ModelRenderer disable15;
	private final ModelRenderer disable16;
	private final ModelRenderer disable17;
	private final ModelRenderer disable18;
	private final ModelRenderer hex_main_4;
	private final ModelRenderer eye4;
	private final ModelRenderer ball5;
	private final ModelRenderer enable19;
	private final ModelRenderer enable20;
	private final ModelRenderer enable21;
	private final ModelRenderer enable22;
	private final ModelRenderer enable23;
	private final ModelRenderer enable24;
	private final ModelRenderer disable19;
	private final ModelRenderer disable20;
	private final ModelRenderer disable21;
	private final ModelRenderer disable22;
	private final ModelRenderer disable23;
	private final ModelRenderer disable24;
	private final ModelRenderer part3;
	private final ModelRenderer hex_main_5;
	private final ModelRenderer eye5;
	private final ModelRenderer ball6;
	private final ModelRenderer enable25;
	private final ModelRenderer enable26;
	private final ModelRenderer enable27;
	private final ModelRenderer enable28;
	private final ModelRenderer enable29;
	private final ModelRenderer enable30;
	private final ModelRenderer disable25;
	private final ModelRenderer disable26;
	private final ModelRenderer disable27;
	private final ModelRenderer disable28;
	private final ModelRenderer disable29;
	private final ModelRenderer disable30;
	private final ModelRenderer hex_main_6;
	private final ModelRenderer eye6;
	private final ModelRenderer ball7;
	private final ModelRenderer enable31;
	private final ModelRenderer enable32;
	private final ModelRenderer enable33;
	private final ModelRenderer enable34;
	private final ModelRenderer enable35;
	private final ModelRenderer enable36;
	private final ModelRenderer disable31;
	private final ModelRenderer disable32;
	private final ModelRenderer disable33;
	private final ModelRenderer disable34;
	private final ModelRenderer disable35;
	private final ModelRenderer disable36;
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
		ball2.cubeList.add(new ModelBox(ball2, 19, 0, -1.0F, -0.9995F, -6.5F, 2, 2, 2, 0.0F, false));

		side2 = new ModelRenderer(this);
		side2.setRotationPoint(0.0F, 0.0F, 0.0F);
		eye1.addChild(side2);
		side2.cubeList.add(new ModelBox(side2, 0, 2, 3.0F, -0.4995F, -5.5F, 3, 1, 0, 0.0F, false));
		side2.cubeList.add(new ModelBox(side2, 0, 1, -1.5F, -2.2995F, -5.5F, 3, 1, 0, 0.0F, false));

		eyeLineOutDown_r1 = new ModelRenderer(this);
		eyeLineOutDown_r1.setRotationPoint(2.3601F, -0.8495F, 1.0F);
		side2.addChild(eyeLineOutDown_r1);
		setRotationAngle(eyeLineOutDown_r1, 0.0F, 0.0F, -0.7854F);
		eyeLineOutDown_r1.cubeList.add(new ModelBox(eyeLineOutDown_r1, 0, 0, -2.8F, 0.6F, -6.5F, 3, 1, 0, 0.0F, false));

		eyeLineOutUp_r1 = new ModelRenderer(this);
		eyeLineOutUp_r1.setRotationPoint(2.3601F, -0.8495F, 1.0F);
		side2.addChild(eyeLineOutUp_r1);
		setRotationAngle(eyeLineOutUp_r1, 0.0F, 0.0F, 0.7854F);
		eyeLineOutUp_r1.cubeList.add(new ModelBox(eyeLineOutUp_r1, 0, 0, -1.6F, -0.4F, -6.5F, 3, 1, 0, 0.0F, false));

		mirror2 = new ModelRenderer(this);
		mirror2.setRotationPoint(0.0F, 0.0005F, 0.0F);
		eye1.addChild(mirror2);
		setRotationAngle(mirror2, 0.0F, 0.0F, -3.1416F);
		mirror2.cubeList.add(new ModelBox(mirror2, 0, 2, 3.0F, -0.5F, -5.5F, 3, 1, 0, 0.0F, false));
		mirror2.cubeList.add(new ModelBox(mirror2, 0, 1, -1.5F, -2.3F, -5.5F, 3, 1, 0, 0.0F, false));

		eyeLineOutDown_r2 = new ModelRenderer(this);
		eyeLineOutDown_r2.setRotationPoint(2.3601F, -0.85F, 0.0F);
		mirror2.addChild(eyeLineOutDown_r2);
		setRotationAngle(eyeLineOutDown_r2, 0.0F, 0.0F, -0.7854F);
		eyeLineOutDown_r2.cubeList.add(new ModelBox(eyeLineOutDown_r2, 0, 0, -2.8F, 0.6F, -5.5F, 3, 1, 0, 0.0F, false));

		eyeLineOutUp_r2 = new ModelRenderer(this);
		eyeLineOutUp_r2.setRotationPoint(2.3601F, -0.85F, 0.0F);
		mirror2.addChild(eyeLineOutUp_r2);
		setRotationAngle(eyeLineOutUp_r2, 0.0F, 0.0F, 0.7854F);
		eyeLineOutUp_r2.cubeList.add(new ModelBox(eyeLineOutUp_r2, 0, 0, -1.6F, -0.4F, -5.5F, 3, 1, 0, 0.0F, false));

		enable1 = new ModelRenderer(this);
		enable1.setRotationPoint(-3.7082F, -11.4127F, -6.0F);
		hex_main_1.addChild(enable1);
		setRotationAngle(enable1, 0.0F, 0.0F, -0.1571F);
		enable1.cubeList.add(new ModelBox(enable1, 60, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable2 = new ModelRenderer(this);
		enable2.setRotationPoint(-3.7082F, -11.4127F, -6.0F);
		hex_main_1.addChild(enable2);
		setRotationAngle(enable2, 0.0F, 0.0F, -0.3142F);
		enable2.cubeList.add(new ModelBox(enable2, 56, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable3 = new ModelRenderer(this);
		enable3.setRotationPoint(-5.4479F, -10.6921F, -6.0F);
		hex_main_1.addChild(enable3);
		setRotationAngle(enable3, 0.0F, 0.0F, -0.4712F);
		enable3.cubeList.add(new ModelBox(enable3, 52, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable4 = new ModelRenderer(this);
		enable4.setRotationPoint(-7.0534F, -9.7082F, -6.0F);
		hex_main_1.addChild(enable4);
		setRotationAngle(enable4, 0.0F, 0.0F, -0.6283F);
		enable4.cubeList.add(new ModelBox(enable4, 60, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable5 = new ModelRenderer(this);
		enable5.setRotationPoint(-8.4853F, -8.4853F, -6.0F);
		hex_main_1.addChild(enable5);
		setRotationAngle(enable5, 0.0F, 0.0F, -0.7854F);
		enable5.cubeList.add(new ModelBox(enable5, 56, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable6 = new ModelRenderer(this);
		enable6.setRotationPoint(-9.7082F, -7.0534F, -6.0F);
		hex_main_1.addChild(enable6);
		setRotationAngle(enable6, 0.0F, 0.0F, -0.9425F);
		enable6.cubeList.add(new ModelBox(enable6, 52, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		disable1 = new ModelRenderer(this);
		disable1.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(disable1);
		setRotationAngle(disable1, 0.0F, 0.0F, -0.1571F);
		disable1.cubeList.add(new ModelBox(disable1, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable2 = new ModelRenderer(this);
		disable2.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(disable2);
		setRotationAngle(disable2, 0.0F, 0.0F, -0.3142F);
		disable2.cubeList.add(new ModelBox(disable2, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable3 = new ModelRenderer(this);
		disable3.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(disable3);
		setRotationAngle(disable3, 0.0F, 0.0F, -0.4712F);
		disable3.cubeList.add(new ModelBox(disable3, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable4 = new ModelRenderer(this);
		disable4.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(disable4);
		setRotationAngle(disable4, 0.0F, 0.0F, -0.6283F);
		disable4.cubeList.add(new ModelBox(disable4, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable5 = new ModelRenderer(this);
		disable5.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(disable5);
		setRotationAngle(disable5, 0.0F, 0.0F, -0.7854F);
		disable5.cubeList.add(new ModelBox(disable5, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable6 = new ModelRenderer(this);
		disable6.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_1.addChild(disable6);
		setRotationAngle(disable6, 0.0F, 0.0F, -0.9425F);
		disable6.cubeList.add(new ModelBox(disable6, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

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
		ball3.cubeList.add(new ModelBox(ball3, 19, 0, -1.0F, -0.9995F, -6.5F, 2, 2, 2, 0.0F, false));

		enable7 = new ModelRenderer(this);
		enable7.setRotationPoint(-1.8772F, -11.8523F, -6.0F);
		hex_main_2.addChild(enable7);
		setRotationAngle(enable7, 0.0F, 0.0F, -0.1571F);
		enable7.cubeList.add(new ModelBox(enable7, 52, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable8 = new ModelRenderer(this);
		enable8.setRotationPoint(-3.7082F, -11.4127F, -6.0F);
		hex_main_2.addChild(enable8);
		setRotationAngle(enable8, 0.0F, 0.0F, -0.3142F);
		enable8.cubeList.add(new ModelBox(enable8, 56, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable9 = new ModelRenderer(this);
		enable9.setRotationPoint(-5.4479F, -10.6921F, -6.0F);
		hex_main_2.addChild(enable9);
		setRotationAngle(enable9, 0.0F, 0.0F, -0.4712F);
		enable9.cubeList.add(new ModelBox(enable9, 60, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable10 = new ModelRenderer(this);
		enable10.setRotationPoint(-7.0534F, -9.7082F, -6.0F);
		hex_main_2.addChild(enable10);
		setRotationAngle(enable10, 0.0F, 0.0F, -0.6283F);
		enable10.cubeList.add(new ModelBox(enable10, 52, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable11 = new ModelRenderer(this);
		enable11.setRotationPoint(-8.4853F, -8.4853F, -6.0F);
		hex_main_2.addChild(enable11);
		setRotationAngle(enable11, 0.0F, 0.0F, -0.7854F);
		enable11.cubeList.add(new ModelBox(enable11, 56, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable12 = new ModelRenderer(this);
		enable12.setRotationPoint(-9.7082F, -7.0534F, -6.0F);
		hex_main_2.addChild(enable12);
		setRotationAngle(enable12, 0.0F, 0.0F, -0.9425F);
		enable12.cubeList.add(new ModelBox(enable12, 60, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		disable7 = new ModelRenderer(this);
		disable7.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(disable7);
		setRotationAngle(disable7, 0.0F, 0.0F, -0.1571F);
		disable7.cubeList.add(new ModelBox(disable7, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable8 = new ModelRenderer(this);
		disable8.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(disable8);
		setRotationAngle(disable8, 0.0F, 0.0F, -0.3142F);
		disable8.cubeList.add(new ModelBox(disable8, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable9 = new ModelRenderer(this);
		disable9.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(disable9);
		setRotationAngle(disable9, 0.0F, 0.0F, -0.4712F);
		disable9.cubeList.add(new ModelBox(disable9, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable10 = new ModelRenderer(this);
		disable10.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(disable10);
		setRotationAngle(disable10, 0.0F, 0.0F, -0.6283F);
		disable10.cubeList.add(new ModelBox(disable10, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable11 = new ModelRenderer(this);
		disable11.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(disable11);
		setRotationAngle(disable11, 0.0F, 0.0F, -0.7854F);
		disable11.cubeList.add(new ModelBox(disable11, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable12 = new ModelRenderer(this);
		disable12.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_2.addChild(disable12);
		setRotationAngle(disable12, 0.0F, 0.0F, -0.9425F);
		disable12.cubeList.add(new ModelBox(disable12, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

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
		ball4.cubeList.add(new ModelBox(ball4, 19, 0, -1.0F, -0.9995F, -6.5F, 2, 2, 2, 0.0F, false));

		enable13 = new ModelRenderer(this);
		enable13.setRotationPoint(-1.8772F, -11.8523F, -6.0F);
		hex_main_3.addChild(enable13);
		setRotationAngle(enable13, 0.0F, 0.0F, -0.1571F);
		enable13.cubeList.add(new ModelBox(enable13, 60, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable14 = new ModelRenderer(this);
		enable14.setRotationPoint(-3.7082F, -11.4127F, -6.0F);
		hex_main_3.addChild(enable14);
		setRotationAngle(enable14, 0.0F, 0.0F, -0.3142F);
		enable14.cubeList.add(new ModelBox(enable14, 56, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable15 = new ModelRenderer(this);
		enable15.setRotationPoint(-5.4479F, -10.6921F, -6.0F);
		hex_main_3.addChild(enable15);
		setRotationAngle(enable15, 0.0F, 0.0F, -0.4712F);
		enable15.cubeList.add(new ModelBox(enable15, 52, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable16 = new ModelRenderer(this);
		enable16.setRotationPoint(-7.0534F, -9.7082F, -6.0F);
		hex_main_3.addChild(enable16);
		setRotationAngle(enable16, 0.0F, 0.0F, -0.6283F);
		enable16.cubeList.add(new ModelBox(enable16, 60, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable17 = new ModelRenderer(this);
		enable17.setRotationPoint(-8.4853F, -8.4853F, -6.0F);
		hex_main_3.addChild(enable17);
		setRotationAngle(enable17, 0.0F, 0.0F, -0.7854F);
		enable17.cubeList.add(new ModelBox(enable17, 56, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable18 = new ModelRenderer(this);
		enable18.setRotationPoint(-9.7082F, -7.0534F, -6.0F);
		hex_main_3.addChild(enable18);
		setRotationAngle(enable18, 0.0F, 0.0F, -0.9425F);
		enable18.cubeList.add(new ModelBox(enable18, 52, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		disable13 = new ModelRenderer(this);
		disable13.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(disable13);
		setRotationAngle(disable13, 0.0F, 0.0F, -0.1571F);
		disable13.cubeList.add(new ModelBox(disable13, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable14 = new ModelRenderer(this);
		disable14.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(disable14);
		setRotationAngle(disable14, 0.0F, 0.0F, -0.3142F);
		disable14.cubeList.add(new ModelBox(disable14, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable15 = new ModelRenderer(this);
		disable15.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(disable15);
		setRotationAngle(disable15, 0.0F, 0.0F, -0.4712F);
		disable15.cubeList.add(new ModelBox(disable15, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable16 = new ModelRenderer(this);
		disable16.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(disable16);
		setRotationAngle(disable16, 0.0F, 0.0F, -0.6283F);
		disable16.cubeList.add(new ModelBox(disable16, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable17 = new ModelRenderer(this);
		disable17.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(disable17);
		setRotationAngle(disable17, 0.0F, 0.0F, -0.7854F);
		disable17.cubeList.add(new ModelBox(disable17, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable18 = new ModelRenderer(this);
		disable18.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_3.addChild(disable18);
		setRotationAngle(disable18, 0.0F, 0.0F, -0.9425F);
		disable18.cubeList.add(new ModelBox(disable18, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

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
		ball5.cubeList.add(new ModelBox(ball5, 19, 0, -1.0F, -0.9995F, -6.5F, 2, 2, 2, 0.0F, false));

		enable19 = new ModelRenderer(this);
		enable19.setRotationPoint(-1.8772F, -11.8523F, -6.0F);
		hex_main_4.addChild(enable19);
		setRotationAngle(enable19, 0.0F, 0.0F, -0.1571F);
		enable19.cubeList.add(new ModelBox(enable19, 52, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable20 = new ModelRenderer(this);
		enable20.setRotationPoint(-3.7082F, -11.4127F, -6.0F);
		hex_main_4.addChild(enable20);
		setRotationAngle(enable20, 0.0F, 0.0F, -0.3142F);
		enable20.cubeList.add(new ModelBox(enable20, 56, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable21 = new ModelRenderer(this);
		enable21.setRotationPoint(-5.4479F, -10.6921F, -6.0F);
		hex_main_4.addChild(enable21);
		setRotationAngle(enable21, 0.0F, 0.0F, -0.4712F);
		enable21.cubeList.add(new ModelBox(enable21, 60, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable22 = new ModelRenderer(this);
		enable22.setRotationPoint(-7.0534F, -9.7082F, -6.0F);
		hex_main_4.addChild(enable22);
		setRotationAngle(enable22, 0.0F, 0.0F, -0.6283F);
		enable22.cubeList.add(new ModelBox(enable22, 52, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable23 = new ModelRenderer(this);
		enable23.setRotationPoint(-8.4853F, -8.4853F, -6.0F);
		hex_main_4.addChild(enable23);
		setRotationAngle(enable23, 0.0F, 0.0F, -0.7854F);
		enable23.cubeList.add(new ModelBox(enable23, 56, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable24 = new ModelRenderer(this);
		enable24.setRotationPoint(-9.7082F, -7.0534F, -6.0F);
		hex_main_4.addChild(enable24);
		setRotationAngle(enable24, 0.0F, 0.0F, -0.9425F);
		enable24.cubeList.add(new ModelBox(enable24, 60, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		disable19 = new ModelRenderer(this);
		disable19.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(disable19);
		setRotationAngle(disable19, 0.0F, 0.0F, -0.1571F);
		disable19.cubeList.add(new ModelBox(disable19, 62, 2, -2.3772F, -12.3523F, -6.0F, 1, 1, 0, 0.0F, false));

		disable20 = new ModelRenderer(this);
		disable20.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(disable20);
		setRotationAngle(disable20, 0.0F, 0.0F, -0.3142F);
		disable20.cubeList.add(new ModelBox(disable20, 62, 2, -4.2082F, -11.9127F, -6.0F, 1, 1, 0, 0.0F, false));

		disable21 = new ModelRenderer(this);
		disable21.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(disable21);
		setRotationAngle(disable21, 0.0F, 0.0F, -0.4712F);
		disable21.cubeList.add(new ModelBox(disable21, 62, 2, -5.9479F, -11.1921F, -6.0F, 1, 1, 0, 0.0F, false));

		disable22 = new ModelRenderer(this);
		disable22.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(disable22);
		setRotationAngle(disable22, 0.0F, 0.0F, -0.6283F);
		disable22.cubeList.add(new ModelBox(disable22, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable23 = new ModelRenderer(this);
		disable23.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(disable23);
		setRotationAngle(disable23, 0.0F, 0.0F, -0.7854F);
		disable23.cubeList.add(new ModelBox(disable23, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable24 = new ModelRenderer(this);
		disable24.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_4.addChild(disable24);
		setRotationAngle(disable24, 0.0F, 0.0F, -0.9425F);
		disable24.cubeList.add(new ModelBox(disable24, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

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
		ball6.cubeList.add(new ModelBox(ball6, 19, 0, -1.0F, -0.9995F, -6.5F, 2, 2, 2, 0.0F, false));

		enable25 = new ModelRenderer(this);
		enable25.setRotationPoint(-1.8772F, -11.8523F, -6.0F);
		hex_main_5.addChild(enable25);
		setRotationAngle(enable25, 0.0F, 0.0F, -0.1571F);
		enable25.cubeList.add(new ModelBox(enable25, 60, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable26 = new ModelRenderer(this);
		enable26.setRotationPoint(-3.7082F, -11.4127F, -6.0F);
		hex_main_5.addChild(enable26);
		setRotationAngle(enable26, 0.0F, 0.0F, -0.3142F);
		enable26.cubeList.add(new ModelBox(enable26, 56, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable27 = new ModelRenderer(this);
		enable27.setRotationPoint(-5.4479F, -10.6921F, -6.0F);
		hex_main_5.addChild(enable27);
		setRotationAngle(enable27, 0.0F, 0.0F, -0.4712F);
		enable27.cubeList.add(new ModelBox(enable27, 52, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable28 = new ModelRenderer(this);
		enable28.setRotationPoint(-7.0534F, -9.7082F, -6.0F);
		hex_main_5.addChild(enable28);
		setRotationAngle(enable28, 0.0F, 0.0F, -0.6283F);
		enable28.cubeList.add(new ModelBox(enable28, 60, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable29 = new ModelRenderer(this);
		enable29.setRotationPoint(-8.4853F, -8.4853F, -6.0F);
		hex_main_5.addChild(enable29);
		setRotationAngle(enable29, 0.0F, 0.0F, -0.7854F);
		enable29.cubeList.add(new ModelBox(enable29, 56, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable30 = new ModelRenderer(this);
		enable30.setRotationPoint(-9.7082F, -7.0534F, -6.0F);
		hex_main_5.addChild(enable30);
		setRotationAngle(enable30, 0.0F, 0.0F, -0.9425F);
		enable30.cubeList.add(new ModelBox(enable30, 52, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		disable25 = new ModelRenderer(this);
		disable25.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(disable25);
		setRotationAngle(disable25, 0.0F, 0.0F, -0.1571F);
		disable25.cubeList.add(new ModelBox(disable25, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable26 = new ModelRenderer(this);
		disable26.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(disable26);
		setRotationAngle(disable26, 0.0F, 0.0F, -0.3142F);
		disable26.cubeList.add(new ModelBox(disable26, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable27 = new ModelRenderer(this);
		disable27.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(disable27);
		setRotationAngle(disable27, 0.0F, 0.0F, -0.4712F);
		disable27.cubeList.add(new ModelBox(disable27, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable28 = new ModelRenderer(this);
		disable28.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(disable28);
		setRotationAngle(disable28, 0.0F, 0.0F, -0.6283F);
		disable28.cubeList.add(new ModelBox(disable28, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable29 = new ModelRenderer(this);
		disable29.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(disable29);
		setRotationAngle(disable29, 0.0F, 0.0F, -0.7854F);
		disable29.cubeList.add(new ModelBox(disable29, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable30 = new ModelRenderer(this);
		disable30.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_5.addChild(disable30);
		setRotationAngle(disable30, 0.0F, 0.0F, -0.9425F);
		disable30.cubeList.add(new ModelBox(disable30, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

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
		ball7.cubeList.add(new ModelBox(ball7, 19, 0, -1.0F, -0.9995F, -6.5F, 2, 2, 2, 0.0F, false));

		enable31 = new ModelRenderer(this);
		enable31.setRotationPoint(-1.8772F, -11.8523F, -6.0F);
		hex_main_6.addChild(enable31);
		setRotationAngle(enable31, 0.0F, 0.0F, -0.1571F);
		enable31.cubeList.add(new ModelBox(enable31, 52, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable32 = new ModelRenderer(this);
		enable32.setRotationPoint(-3.7082F, -11.4127F, -6.0F);
		hex_main_6.addChild(enable32);
		setRotationAngle(enable32, 0.0F, 0.0F, -0.3142F);
		enable32.cubeList.add(new ModelBox(enable32, 56, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable33 = new ModelRenderer(this);
		enable33.setRotationPoint(-5.4479F, -10.6921F, -6.0F);
		hex_main_6.addChild(enable33);
		setRotationAngle(enable33, 0.0F, 0.0F, -0.4712F);
		enable33.cubeList.add(new ModelBox(enable33, 60, 3, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable34 = new ModelRenderer(this);
		enable34.setRotationPoint(-7.0534F, -9.7082F, -6.0F);
		hex_main_6.addChild(enable34);
		setRotationAngle(enable34, 0.0F, 0.0F, -0.6283F);
		enable34.cubeList.add(new ModelBox(enable34, 52, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable35 = new ModelRenderer(this);
		enable35.setRotationPoint(-8.4853F, -8.4853F, -6.0F);
		hex_main_6.addChild(enable35);
		setRotationAngle(enable35, 0.0F, 0.0F, -0.7854F);
		enable35.cubeList.add(new ModelBox(enable35, 56, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		enable36 = new ModelRenderer(this);
		enable36.setRotationPoint(-9.7082F, -7.0534F, -6.0F);
		hex_main_6.addChild(enable36);
		setRotationAngle(enable36, 0.0F, 0.0F, -0.9425F);
		enable36.cubeList.add(new ModelBox(enable36, 60, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F, false));

		disable31 = new ModelRenderer(this);
		disable31.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(disable31);
		setRotationAngle(disable31, 0.0F, 0.0F, -0.1571F);
		disable31.cubeList.add(new ModelBox(disable31, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable32 = new ModelRenderer(this);
		disable32.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(disable32);
		setRotationAngle(disable32, 0.0F, 0.0F, -0.3142F);
		disable32.cubeList.add(new ModelBox(disable32, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable33 = new ModelRenderer(this);
		disable33.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(disable33);
		setRotationAngle(disable33, 0.0F, 0.0F, -0.4712F);
		disable33.cubeList.add(new ModelBox(disable33, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable34 = new ModelRenderer(this);
		disable34.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(disable34);
		setRotationAngle(disable34, 0.0F, 0.0F, -0.6283F);
		disable34.cubeList.add(new ModelBox(disable34, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable35 = new ModelRenderer(this);
		disable35.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(disable35);
		setRotationAngle(disable35, 0.0F, 0.0F, -0.7854F);
		disable35.cubeList.add(new ModelBox(disable35, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		disable36 = new ModelRenderer(this);
		disable36.setRotationPoint(0.0F, 0.0F, 0.0F);
		hex_main_6.addChild(disable36);
		setRotationAngle(disable36, 0.0F, 0.0F, -0.9425F);
		disable36.cubeList.add(new ModelBox(disable36, 62, 2, -0.5F, -12.5F, -6.0F, 1, 1, 0, 0.0F, false));

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -3.0F, -28.0F, -4.0F, 6, 28, 7, 0.0F, false));

		nodeListLit = new ModelRenderer[]
				{
						enable1,
						enable2,
						enable3,
						enable4,
						enable5,
						enable6,
						enable7,
						enable8,
						enable9,
						enable10,
						enable11,
						enable12,
						enable13,
						enable14,
						enable15,
						enable16,
						enable17,
						enable18,
						enable19,
						enable20,
						enable21,
						enable22,
						enable23,
						enable24,
						enable25,
						enable26,
						enable27,
						enable28,
						enable29,
						enable30,
						enable31,
						enable32,
						enable33,
						enable34,
						enable35,
						enable36,

				};

		nodeListUnlit = new ModelRenderer[] {
				disable1,
				disable2,
				disable3,
				disable4,
				disable5,
				disable6,
				disable7,
				disable8,
				disable9,
				disable10,
				disable11,
				disable12,
				disable13,
				disable14,
				disable15,
				disable16,
				disable17,
				disable18,
				disable19,
				disable20,
				disable21,
				disable22,
				disable23,
				disable24,
				disable25,
				disable26,
				disable27,
				disable28,
				disable29,
				disable30,
				disable31,
				disable32,
				disable33,
				disable34,
				disable35,
				disable36,
		};

		eyeList = new ModelRenderer[]
				{
						ball2,eye2,eye3,eye4,eye5,eye6,
				};
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		ringCenter.render(f5);
		bb_main.render(f5);

		int length = nodeListLit.length;
		for (int i = 0; i < length; i++) {
			nodeListLit[i].rotateAngleY = entity.ticksExisted * ModConfig.DEBUG_CONF.RAIDEN_ROTATION_SPEED;
			nodeListLit[i].rotateAngleX = entity.ticksExisted * ModConfig.DEBUG_CONF.RAIDEN_ROTATION_SPEED;
		}

		length = eyeList.length;
		for (int i = 0; i < length; i++) {
			float cur = (float) i / length;
			eyeList[i].rotateAngleZ = entity.ticksExisted * ModConfig.DEBUG_CONF.RAIDEN_ROTATION_SPEED;
		}
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRatio(float ratio)
	{
		int length = nodeListLit.length;
		for (int i = 0; i < length; i++) {
			float cur = (float) i / length;
			boolean isReached = cur <= ratio;
			nodeListLit[i].isHidden = !isReached;
			nodeListUnlit[i].isHidden = isReached;
		}

		length = eyeList.length;
		for (int i = 0; i < length; i++) {
			float cur = (float) i / length;
			boolean isReached = cur <= ratio;
			eyeList[i].isHidden = !isReached;
		}
	}
}