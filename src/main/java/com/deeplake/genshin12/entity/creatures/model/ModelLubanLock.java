package com.deeplake.genshin12.entity.creatures.model;// Made with Blockbench 4.1.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLubanLock extends ModelBase {
	private final ModelRenderer base;
	private final ModelRenderer Y;
	private final ModelRenderer rock_light_r1;
	private final ModelRenderer X;
	private final ModelRenderer rock_light_r2;
	private final ModelRenderer Z;
	private final ModelRenderer rock_light_r3;

	public ModelLubanLock() {
		textureWidth = 64;
		textureHeight = 64;

		base = new ModelRenderer(this);
		base.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		Y = new ModelRenderer(this);
		Y.setRotationPoint(0.0F, 0.0F, 0.0F);
		base.addChild(Y);
		Y.cubeList.add(new ModelBox(Y, 0, 0, -2.0F, -16.0F, -4.0F, 4, 16, 8, 0.0F, false));

		rock_light_r1 = new ModelRenderer(this);
		rock_light_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		Y.addChild(rock_light_r1);
		setRotationAngle(rock_light_r1, 0.0F, -1.5708F, 0.0F);
		rock_light_r1.cubeList.add(new ModelBox(rock_light_r1, 14, 14, -1.0F, -14.0F, -5.0F, 2, 12, 10, 0.0F, false));

		X = new ModelRenderer(this);
		X.setRotationPoint(0.0F, -8.0F, 0.0F);
		base.addChild(X);
		setRotationAngle(X, -1.5708F, 0.0F, 1.5708F);
		X.cubeList.add(new ModelBox(X, 0, 0, -2.0F, -8.0F, -4.0F, 4, 16, 8, 0.0F, false));

		rock_light_r2 = new ModelRenderer(this);
		rock_light_r2.setRotationPoint(0.0F, 8.0F, 0.0F);
		X.addChild(rock_light_r2);
		setRotationAngle(rock_light_r2, 0.0F, -1.5708F, 0.0F);
		rock_light_r2.cubeList.add(new ModelBox(rock_light_r2, 14, 14, -1.0F, -14.0F, -5.0F, 2, 12, 10, 0.0F, false));

		Z = new ModelRenderer(this);
		Z.setRotationPoint(0.0F, -8.0F, 0.0F);
		base.addChild(Z);
		setRotationAngle(Z, 0.0F, -1.5708F, 1.5708F);
		Z.cubeList.add(new ModelBox(Z, 0, 0, -2.0F, -8.0F, -4.0F, 4, 16, 8, 0.0F, false));

		rock_light_r3 = new ModelRenderer(this);
		rock_light_r3.setRotationPoint(0.0F, 8.0F, 0.0F);
		Z.addChild(rock_light_r3);
		setRotationAngle(rock_light_r3, 0.0F, -1.5708F, 0.0F);
		rock_light_r3.cubeList.add(new ModelBox(rock_light_r3, 14, 14, -1.0F, -14.0F, -5.0F, 2, 12, 10, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		base.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}