package com.faithl.level.module.trait

internal object Permission {

//    @SubscribeEvent
//    fun e(e: LevelUpdateEvent) {
//        if (e.type == ChangeType.TAKE) {
//            return
//        }
//        val list = BasicLevel.getLevelFunc(e.basicLevel).permission?.getStringList("player_level_up") ?: return
//        val permission = BasicLevel.getPlayerData(e.basicLevel, e.player).getLevelValue(e.newLevel, list)
//        if (permission != null) {
//            if (permission == "null") {
//                return
//            }
//            if (!e.player.hasPermission(permission)) {
//                e.isCancelled = true
//            }
//        }
//    }
//
//    @SubscribeEvent
//    fun e(e: ExpUpdateEvent) {
//        if (e.type == ChangeType.TAKE) {
//            return
//        }
//        val list = BasicLevel.getLevelFunc(e.basicLevel).permission?.getStringList("player_exp_up") ?: return
//        val permission = BasicLevel.getPlayerData(e.basicLevel, e.player)
//            .getLevelValue(FaithlLevelAPI.getPlayerData(e.basicLevel, e.player).playerLevel, list)
//        if (permission != null) {
//            if (permission == "null") {
//                return
//            }
//            if (!e.player.hasPermission(permission)) {
//                e.isCancelled = true
//            }
//        }
//    }

}