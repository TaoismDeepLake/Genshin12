package com.deeplake.genshin12.util.NBTStrDef;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

import static com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef.*;

//on a server, strlen 65000 is ok, but 66000 will crash
public class IDLNBTUtil {
	public static NBTTagCompound getNBT(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		return nbt;
	}

	public static NBTTagCompound getNBT(Entity entity) {
		NBTTagCompound nbt = entity.getEntityData();
		return nbt;
	}


	public static NBTTagCompound getNBT(NBTTagCompound tag) {
		if(tag == null) {
			return new NBTTagCompound();
		}

		return tag;
	}

	//writeEntityToNBT
	//readEntityFromNBT

	@Nullable
	public static boolean StackHasKey(ItemStack stack, String key) {
		return !(stack.isEmpty() || !getNBT(stack).hasKey(key));
	}

	//Boolean
	public static boolean SetBoolean(ItemStack stack, String key, boolean value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setBoolean(key, value);
		return true;
	}

	public static boolean GetBoolean(ItemStack stack, String key, boolean defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getBoolean(key);
		}
		else
		{
			return defaultVal;
		}
	}

	public static boolean GetBoolean(ItemStack stack, String key)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getBoolean(key);
		}
		else
		{
			return false;
		}
	}
	//get with default val
	public static boolean GetBooleanDF(ItemStack stack, String key, boolean defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getBoolean(key);
		}
		else
		{
			return defaultVal;
		}
	}

	//Double
	public static boolean SetDouble(ItemStack stack, String key, double value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setDouble(key, value);
		return true;
	}

	public static double GetDouble(ItemStack stack, String key, double defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getDouble(key);
		}
		else
		{
			return defaultVal;
		}
	}

	//Integer
	public static boolean SetLong(ItemStack stack, String key, long value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setLong(key, value);
		return true;
	}

	public static boolean SetState(ItemStack stack, int value)
	{
		return SetInt(stack, STATE, value);
	}

	public static boolean SetInt(ItemStack stack, String key, int value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setInteger(key, value);
		return true;
	}
	//Used for state on-off things.
	public static boolean switchState(ItemStack stack)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setInteger(STATE, nbt.getInteger(STATE) > 0 ? 0 : 1);
		return true;
	}

	public static boolean setIntOptimized(ItemStack stack, String key, int value)
	{
		NBTTagCompound nbt = getNBT(stack);
		if (nbt.getInteger(key) != value)
		{
			nbt.setInteger(key, value);
		}
		return true;
	}

	public static boolean SetInt(Entity entity, String key, int value)
	{
		NBTTagCompound nbt = getNBT(entity);
		nbt.setInteger(key, value);
		return true;
	}

	public static boolean setIntAuto(Entity entity, String key, int value)
	{
		if (entity instanceof EntityPlayer)
		{
			setPlayerIdeallandTagSafe((EntityPlayer) entity, key, value);
			return true;
		}
		NBTTagCompound nbt = getNBT(entity);
		nbt.setInteger(key, value);
		return true;
	}

	public static boolean addIntAuto(Entity entity, String key, int value)
	{
		int oldVal = GetIntAuto(entity, key, 0);
		setIntAuto(entity, key, value + oldVal);
		return true;
	}

	public static boolean addInt(ItemStack stack, String key, int value) {
		int oldVal = GetInt(stack, key, 0);
		SetInt(stack, key, value + oldVal);
		return true;
	}

	public static int GetInt(Entity entity, String key, int defaultVal)
	{
		if (EntityHasKey(entity, key))
		{
			NBTTagCompound nbt = getNBT(entity);
			return nbt.getInteger(key);
		}
		else
		{
			return defaultVal;
		}
	}

	public static int GetIntAuto(Entity entity, String key, int defaultVal)
	{
		if (entity instanceof EntityPlayer)
		{
			return getPlayerIdeallandIntSafe((EntityPlayer) entity, key);
		}

		if (EntityHasKey(entity, key))
		{
			NBTTagCompound nbt = getNBT(entity);
			return nbt.getInteger(key);
		}
		else
		{
			return defaultVal;
		}
	}

	public static int GetState(ItemStack stack)
	{
		return GetInt(stack, STATE);
	}

	public static int GetInt(ItemStack stack, String key, int defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getInteger(key);
		}
		else
		{
			return defaultVal;
		}
	}

	public static long GetLong(ItemStack stack, String key, int defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getLong(key);
		}
		else
		{
			return defaultVal;
		}
	}


	public static int GetInt(ItemStack stack, String key)
	{
		return GetInt(stack, key, 0);
	}

	//String
	public static String GetString(ItemStack stack, String key, String defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getString(key);
		}
		else
		{
			return defaultVal;
		}
	}

	public static String getStringAuto(Entity entity, String key, String defaultVal)
	{
		if (entity instanceof EntityPlayer)
		{
			return getPlayerIdeallandStrSafe((EntityPlayer) entity, key);
		}

		if (EntityHasKey(entity, key))
		{
			NBTTagCompound nbt = getNBT(entity);
			return nbt.getString(key);
		}
		else
		{
			return defaultVal;
		}
	}

	public static boolean SetString(ItemStack stack, String key, String value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setString(key, value);

		return true;
	}


	//entity
	@Nullable
	public static boolean EntityHasKey(Entity entity, String key)
	{
		return getNBT(entity).hasKey(key);
	}

	//Boolean
	public static boolean GetBoolean(Entity entity, String key, boolean defaultVal)
	{
		if (EntityHasKey(entity, key))
		{
			NBTTagCompound nbt = getNBT(entity);
			return nbt.getBoolean(key);
		}
		else
		{
			return defaultVal;
		}
	}

	public static boolean SetBoolean(Entity stack, String key, boolean value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setBoolean(key, value);
		return true;
	}

	public static boolean SetString(Entity stack, String key, String value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setString(key, value);
		return true;
	}

	public static int[] GetIntArray(ItemStack stack, String key)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getIntArray(key);
		}
		else
		{
			return new int[0];
		}
	}

	public static int[] GetIntArray(EntityLivingBase stack, String key)
	{
		if (EntityHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getIntArray(key);
		}
		else
		{
			return new int[0];
		}
	}

	public static void SetIntArray(ItemStack stack, String key, int[] array)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setIntArray(key, array);
	}

	public static void SetGuaEnhanceFree(ItemStack stack, int val)
	{
		SetInt(stack, GUA_FREE_SOCKET, val);
	}

	//--------------------------------------------



	public static BlockPos getMarkedPos(ItemStack stack)
	{
		NBTTagCompound NBT = IDLNBTUtil.getNBT(stack);
		return new BlockPos(NBT.getDouble(ANCHOR_X), NBT.getDouble(ANCHOR_Y), NBT.getDouble(ANCHOR_Z));
	}

	public static BlockPos getMarkedPos2(ItemStack stack)
	{
		NBTTagCompound NBT = IDLNBTUtil.getNBT(stack);
		return new BlockPos(NBT.getDouble(ANCHOR_X_2), NBT.getDouble(ANCHOR_Y_2), NBT.getDouble(ANCHOR_Z_2));
	}

	public static void markPosToStack(ItemStack stack, BlockPos pos)
	{
		IDLNBTUtil.SetBoolean(stack, ANCHOR_READY, true);
		IDLNBTUtil.SetDouble(stack, ANCHOR_X, pos.getX());
		IDLNBTUtil.SetDouble(stack, ANCHOR_Y, pos.getY());
		IDLNBTUtil.SetDouble(stack, ANCHOR_Z, pos.getZ());
	}

	public static void markPosToStack2(ItemStack stack, BlockPos pos)
	{
		IDLNBTUtil.SetBoolean(stack, ANCHOR_READY_2, true);
		IDLNBTUtil.SetDouble(stack, ANCHOR_X_2, pos.getX());
		IDLNBTUtil.SetDouble(stack, ANCHOR_Y_2, pos.getY());
		IDLNBTUtil.SetDouble(stack, ANCHOR_Z_2, pos.getZ());
	}

	public static NBTTagCompound getTagSafe(NBTTagCompound tag, String key) {
		if (tag == null) {
			return new NBTTagCompound();
		}

		return tag.getCompoundTag(key);
	}

	public static NBTTagCompound getPlyrIdlTagSafe(EntityPlayer player) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getTagSafe(data, IDEALLAND);

		return idl_data;
	}

	public static NBTTagCompound getPlayerIdeallandTagGroupSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getCompoundTag(key);
	}

	public static int[] getPlayerIdeallandIntArraySafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getIntArray(key);
	}

	public static long getPlayerIdeallandLongSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getLong(key);
	}

	public static int getPlayerIdeallandIntSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getInteger(key);
	}

	public static float getPlayerIdeallandFloatSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getFloat(key);
	}

	public static double getPlayerIdeallandDoubleSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getDouble(key);
	}

	public static boolean getPlayerIdeallandBoolSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getBoolean(key);
	}

	public static String getPlayerIdeallandStrSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getString(key);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, int value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setInteger(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, int[] value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setIntArray(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, double value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setDouble(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, long value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setLong(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, boolean value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setBoolean(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, String value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setString(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}
}
