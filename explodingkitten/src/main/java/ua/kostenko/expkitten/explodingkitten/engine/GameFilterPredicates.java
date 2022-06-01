package ua.kostenko.expkitten.explodingkitten.engine;

import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;

import java.util.Map;
import java.util.function.Predicate;

public final class GameFilterPredicates {

    public static final Predicate<Map.Entry<CardAction, Integer>> IS_DEFUSE_PREDICATE = stackEntry -> CardAction.DEFUSE.equals(stackEntry.getKey());
    public static final Predicate<Map.Entry<CardAction, Integer>> IS_CAT_PREDICATE = stackEntry -> CardAction.CATS.equals(stackEntry.getKey());
    public static final Predicate<Map.Entry<CardAction, Integer>> IS_NOT_DEFUSE_PREDICATE = Predicate.not(IS_DEFUSE_PREDICATE);
    public static final Predicate<Map.Entry<CardAction, Integer>> IS_NOT_CAT_PREDICATE = Predicate.not(IS_CAT_PREDICATE);
    public static final Predicate<Map.Entry<CardAction, Integer>> IS_NOT_CAT_AND_NOT_DEFUSE = IS_NOT_CAT_PREDICATE.and(IS_NOT_DEFUSE_PREDICATE);

    private GameFilterPredicates() {
    }

}
