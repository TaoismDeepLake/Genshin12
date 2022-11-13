package com.deeplake.genshin12.entity.creatures.model;// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.CommonFunctions;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelBarbara2 extends ModelBase {
	private final ModelRenderer base;
	private final ModelRenderer ring1;
	private final ModelRenderer line4_r1;
	private final ModelRenderer line3_r1;
	private final ModelRenderer line2_r1;
	private final ModelRenderer line2_r2;
	private final ModelRenderer line2_r3;
	private final ModelRenderer ring2;
	private final ModelRenderer line4_r2;
	private final ModelRenderer line3_r2;
	private final ModelRenderer line2_r4;
	private final ModelRenderer line2_r5;
	private final ModelRenderer line2_r6;
	private final ModelRenderer ring3;
	private final ModelRenderer line4_r3;
	private final ModelRenderer line3_r3;
	private final ModelRenderer line2_r7;
	private final ModelRenderer line2_r8;
	private final ModelRenderer line2_r9;

	public ModelBarbara2() {
		textureWidth = 64;
		textureHeight = 64;

		base = new ModelRenderer(this);
		base.setRotationPoint(0.0F, 10.0F, 0.0F);
		

		ring1 = new ModelRenderer(this);
		ring1.setRotationPoint(0.0F, 0.0F, 0.0F);
		base.addChild(ring1);
		ring1.cubeList.add(new ModelBox(ring1, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line4_r1 = new ModelRenderer(this);
		line4_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring1.addChild(line4_r1);
		setRotationAngle(line4_r1, 0.0F, 1.0472F, 0.0F);
		line4_r1.cubeList.add(new ModelBox(line4_r1, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line3_r1 = new ModelRenderer(this);
		line3_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring1.addChild(line3_r1);
		setRotationAngle(line3_r1, 0.0F, 2.0944F, 0.0F);
		line3_r1.cubeList.add(new ModelBox(line3_r1, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line2_r1 = new ModelRenderer(this);
		line2_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring1.addChild(line2_r1);
		setRotationAngle(line2_r1, 0.0F, 3.1416F, 0.0F);
		line2_r1.cubeList.add(new ModelBox(line2_r1, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line2_r2 = new ModelRenderer(this);
		line2_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring1.addChild(line2_r2);
		setRotationAngle(line2_r2, 0.0F, -1.0472F, 0.0F);
		line2_r2.cubeList.add(new ModelBox(line2_r2, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line2_r3 = new ModelRenderer(this);
		line2_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring1.addChild(line2_r3);
		setRotationAngle(line2_r3, 0.0F, -2.0944F, 0.0F);
		line2_r3.cubeList.add(new ModelBox(line2_r3, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		ring2 = new ModelRenderer(this);
		ring2.setRotationPoint(0.0F, -2.0F, 0.0F);
		base.addChild(ring2);
		ring2.cubeList.add(new ModelBox(ring2, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line4_r2 = new ModelRenderer(this);
		line4_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring2.addChild(line4_r2);
		setRotationAngle(line4_r2, 0.0F, 1.0472F, 0.0F);
		line4_r2.cubeList.add(new ModelBox(line4_r2, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line3_r2 = new ModelRenderer(this);
		line3_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring2.addChild(line3_r2);
		setRotationAngle(line3_r2, 0.0F, 2.0944F, 0.0F);
		line3_r2.cubeList.add(new ModelBox(line3_r2, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line2_r4 = new ModelRenderer(this);
		line2_r4.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring2.addChild(line2_r4);
		setRotationAngle(line2_r4, 0.0F, 3.1416F, 0.0F);
		line2_r4.cubeList.add(new ModelBox(line2_r4, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line2_r5 = new ModelRenderer(this);
		line2_r5.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring2.addChild(line2_r5);
		setRotationAngle(line2_r5, 0.0F, -1.0472F, 0.0F);
		line2_r5.cubeList.add(new ModelBox(line2_r5, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line2_r6 = new ModelRenderer(this);
		line2_r6.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring2.addChild(line2_r6);
		setRotationAngle(line2_r6, 0.0F, -2.0944F, 0.0F);
		line2_r6.cubeList.add(new ModelBox(line2_r6, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		ring3 = new ModelRenderer(this);
		ring3.setRotationPoint(0.0F, -4.0F, 0.0F);
		base.addChild(ring3);
		ring3.cubeList.add(new ModelBox(ring3, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line4_r3 = new ModelRenderer(this);
		line4_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring3.addChild(line4_r3);
		setRotationAngle(line4_r3, 0.0F, 1.0472F, 0.0F);
		line4_r3.cubeList.add(new ModelBox(line4_r3, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line3_r3 = new ModelRenderer(this);
		line3_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring3.addChild(line3_r3);
		setRotationAngle(line3_r3, 0.0F, 2.0944F, 0.0F);
		line3_r3.cubeList.add(new ModelBox(line3_r3, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line2_r7 = new ModelRenderer(this);
		line2_r7.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring3.addChild(line2_r7);
		setRotationAngle(line2_r7, 0.0F, 3.1416F, 0.0F);
		line2_r7.cubeList.add(new ModelBox(line2_r7, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line2_r8 = new ModelRenderer(this);
		line2_r8.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring3.addChild(line2_r8);
		setRotationAngle(line2_r8, 0.0F, -1.0472F, 0.0F);
		line2_r8.cubeList.add(new ModelBox(line2_r8, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));

		line2_r9 = new ModelRenderer(this);
		line2_r9.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring3.addChild(line2_r9);
		setRotationAngle(line2_r9, 0.0F, -2.0944F, 0.0F);
		line2_r9.cubeList.add(new ModelBox(line2_r9, 0, 0, -16.0F, -3.0F, 27.7F, 32, 3, 0, 0.0F, false));
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

		float f = CommonFunctions.interpolateRotation(entitylivingbaseIn.prevRenderYawOffset,
				entitylivingbaseIn.renderYawOffset,
				partialTickTime);

//		The GLState will be transformed by (180 - f) in RenderLivingBase, which means the rotationY of root is untouched.
//		This f is not passed as an argument, so I need to get partialTickTime to calculate it myself.
		base.rotateAngleY = -f * CommonDef.DEG_TO_RAD;
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