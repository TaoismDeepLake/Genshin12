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

public class ModelBarbara extends ModelBase {
	private final ModelRenderer base;
	private final ModelRenderer ring1;
	private final ModelRenderer line4_r1;
	private final ModelRenderer line3_r1;
	private final ModelRenderer line2_r1;
	private final ModelRenderer ring2;
	private final ModelRenderer line5_r1;
	private final ModelRenderer line4_r2;
	private final ModelRenderer line3_r2;
	private final ModelRenderer ring3;
	private final ModelRenderer line6_r1;
	private final ModelRenderer line5_r2;
	private final ModelRenderer line4_r3;
	private final ModelRenderer bb_main;

	public ModelBarbara() {
		textureWidth = 64;
		textureHeight = 64;

		base = new ModelRenderer(this);
		base.setRotationPoint(0.0F, 10.0F, 0.0F);
		

		ring1 = new ModelRenderer(this);
		ring1.setRotationPoint(0.0F, 0.0F, 0.0F);
		base.addChild(ring1);
		ring1.cubeList.add(new ModelBox(ring1, 0, 6, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		line4_r1 = new ModelRenderer(this);
		line4_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring1.addChild(line4_r1);
		setRotationAngle(line4_r1, 0.0F, 1.5708F, 0.0F);
		line4_r1.cubeList.add(new ModelBox(line4_r1, 0, 0, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		line3_r1 = new ModelRenderer(this);
		line3_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring1.addChild(line3_r1);
		setRotationAngle(line3_r1, 0.0F, 3.1416F, 0.0F);
		line3_r1.cubeList.add(new ModelBox(line3_r1, 0, 2, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		line2_r1 = new ModelRenderer(this);
		line2_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring1.addChild(line2_r1);
		setRotationAngle(line2_r1, 0.0F, -1.5708F, 0.0F);
		line2_r1.cubeList.add(new ModelBox(line2_r1, 0, 4, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		ring2 = new ModelRenderer(this);
		ring2.setRotationPoint(0.0F, -2.0F, 0.0F);
		base.addChild(ring2);
		ring2.cubeList.add(new ModelBox(ring2, 0, 6, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		line5_r1 = new ModelRenderer(this);
		line5_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring2.addChild(line5_r1);
		setRotationAngle(line5_r1, 0.0F, 1.5708F, 0.0F);
		line5_r1.cubeList.add(new ModelBox(line5_r1, 0, 0, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		line4_r2 = new ModelRenderer(this);
		line4_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring2.addChild(line4_r2);
		setRotationAngle(line4_r2, 0.0F, 3.1416F, 0.0F);
		line4_r2.cubeList.add(new ModelBox(line4_r2, 0, 2, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		line3_r2 = new ModelRenderer(this);
		line3_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring2.addChild(line3_r2);
		setRotationAngle(line3_r2, 0.0F, -1.5708F, 0.0F);
		line3_r2.cubeList.add(new ModelBox(line3_r2, 0, 4, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		ring3 = new ModelRenderer(this);
		ring3.setRotationPoint(0.0F, -4.0F, 0.0F);
		base.addChild(ring3);
		ring3.cubeList.add(new ModelBox(ring3, 0, 6, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		line6_r1 = new ModelRenderer(this);
		line6_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring3.addChild(line6_r1);
		setRotationAngle(line6_r1, 0.0F, 1.5708F, 0.0F);
		line6_r1.cubeList.add(new ModelBox(line6_r1, 0, 0, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		line5_r2 = new ModelRenderer(this);
		line5_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring3.addChild(line5_r2);
		setRotationAngle(line5_r2, 0.0F, 3.1416F, 0.0F);
		line5_r2.cubeList.add(new ModelBox(line5_r2, 0, 2, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		line4_r3 = new ModelRenderer(this);
		line4_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
		ring3.addChild(line4_r3);
		setRotationAngle(line4_r3, 0.0F, -1.5708F, 0.0F);
		line4_r3.cubeList.add(new ModelBox(line4_r3, 0, 4, -12.0F, -1.0F, 12.0F, 24, 1, 1, 0.0F, false));

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 8, -1.0F, -16.0F, -1.0F, 2, 16, 2, 0.0F, false));
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
//		bb_main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}