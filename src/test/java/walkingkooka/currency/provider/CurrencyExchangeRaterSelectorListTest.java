/*
 * Copyright 2026 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.currency.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.HasCsvStringListTsvStringListTesting2;
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringLikeList;
import walkingkooka.collect.list.StringList;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyExchangeRaterSelectorListTest implements ImmutableListTesting<CurrencyExchangeRaterSelectorList, CurrencyExchangeRaterSelector>,
    HasCsvStringListTsvStringListTesting2<CurrencyExchangeRaterSelectorList>,
    HasOptionalCurrencyExchangeRaterSelectorTesting,
    JsonNodeMarshallerTesting<CurrencyExchangeRaterSelectorList> {

    // fromStringLikeList................................................................................................

    @Test
    public void testFromStringLikeListWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchangeRaterSelectorList.fromStringLikeList(null)
        );
    }

    @Test
    public void testFromStringLikeListWithEmpty() {
        this.fromStringLikeListAndCheck(
            CsvStringList.EMPTY,
            CurrencyExchangeRaterSelectorList.EMPTY
        );
    }

    @Test
    public void testFromStringLikeListWithNotEmpty() {
        this.fromStringLikeListAndCheck(
            CsvStringList.EMPTY.concat(CURRENCY_EXCHANGE_RATER_SELECTOR.text())
                .concat(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR.text()),
            CurrencyExchangeRaterSelectorList.EMPTY.concat(CURRENCY_EXCHANGE_RATER_SELECTOR)
                .concat(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR)
        );
    }

    @Test
    public void testFromStringLikeListWithListIncludesNull() {
        this.fromStringLikeListAndCheck(
            StringList.EMPTY.concat(CURRENCY_EXCHANGE_RATER_SELECTOR.text())
                .concat(null)
                .concat(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR.text()),
            CurrencyExchangeRaterSelectorList.EMPTY.concat(CURRENCY_EXCHANGE_RATER_SELECTOR)
                .concat(null)
                .concat(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR)
        );
    }

    @Test
    public void testFromStringLikeListWithListIncludesEmpty() {
        this.fromStringLikeListAndCheck(
            CsvStringList.EMPTY.concat(CURRENCY_EXCHANGE_RATER_SELECTOR.text())
                .concat("")
                .concat(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR.text()),
            CurrencyExchangeRaterSelectorList.EMPTY.concat(CURRENCY_EXCHANGE_RATER_SELECTOR)
                .concat(null)
                .concat(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR)
        );
    }

    private void fromStringLikeListAndCheck(final StringLikeList stringLikeList,
                                            final CurrencyExchangeRaterSelectorList expected) {
        this.checkEquals(
            expected,
            CurrencyExchangeRaterSelectorList.fromStringLikeList(stringLikeList)
        );
    }

    // setElements......................................................................................................

    @Test
    public void testSetElementsWithDifferent() {
        this.setElementsAndCheck(
            new CurrencyExchangeRaterSelectorList(
                Lists.of(CURRENCY_EXCHANGE_RATER_SELECTOR)
            ),
            Lists.of(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR),
            new CurrencyExchangeRaterSelectorList(
                Lists.of(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR)
            )
        );
    }

    @Test
    public void testSetElementsWithDifferentEmpty() {
        this.setElementsAndCheck(
            new CurrencyExchangeRaterSelectorList(
                Lists.of(CURRENCY_EXCHANGE_RATER_SELECTOR)
            ),
            Lists.empty(),
            CurrencyExchangeRaterSelectorList.EMPTY
        );
    }

    @Test
    public void testSetElementsIncludesNullElement() {
        final List<CurrencyExchangeRaterSelector> list = Lists.of(
            CURRENCY_EXCHANGE_RATER_SELECTOR1,
            null
        );


        this.setElementsAndCheck(
            CurrencyExchangeRaterSelectorList.EMPTY,
            list,
            new CurrencyExchangeRaterSelectorList(list)

        );
    }

    // subList..........................................................................................................

    private final static CurrencyExchangeRaterSelector CURRENCY_EXCHANGE_RATER_SELECTOR1 = CURRENCY_EXCHANGE_RATER_SELECTOR;
    private final static CurrencyExchangeRaterSelector CURRENCY_EXCHANGE_RATER_SELECTOR2 = DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR;
    private final static CurrencyExchangeRaterSelector CURRENCY_EXCHANGE_RATER_SELECTOR3 = CurrencyExchangeRaterSelector.parse("selector-3");
    private final static CurrencyExchangeRaterSelector CURRENCY_EXCHANGE_RATER_SELECTOR4 = CurrencyExchangeRaterSelector.parse("selector-4");
    private final static CurrencyExchangeRaterSelector CURRENCY_EXCHANGE_RATER_SELECTOR5 = CurrencyExchangeRaterSelector.parse("selector-5");

    private final static List<CurrencyExchangeRaterSelector> ELEMENTS = Lists.of(
        CURRENCY_EXCHANGE_RATER_SELECTOR1,
        CURRENCY_EXCHANGE_RATER_SELECTOR2,
        CURRENCY_EXCHANGE_RATER_SELECTOR3,
        CURRENCY_EXCHANGE_RATER_SELECTOR4,
        CURRENCY_EXCHANGE_RATER_SELECTOR5
    );

    @Test
    public void testSubList() {
        this.subListAndCheck(
            new CurrencyExchangeRaterSelectorList(ELEMENTS),
            0,
            2,
            new CurrencyExchangeRaterSelectorList(
                Lists.of(
                    CURRENCY_EXCHANGE_RATER_SELECTOR1,
                    CURRENCY_EXCHANGE_RATER_SELECTOR2
                )
            )
        );
    }

    @Test
    public void testSubList2() {
        this.subListAndCheck(
            new CurrencyExchangeRaterSelectorList(ELEMENTS),
            2,
            4,
            new CurrencyExchangeRaterSelectorList(
                Lists.of(
                    CURRENCY_EXCHANGE_RATER_SELECTOR3,
                    CURRENCY_EXCHANGE_RATER_SELECTOR4
                )
            )
        );
    }

    @Test
    public void testSubListSubList() {
        this.subListAndCheck(
            new CurrencyExchangeRaterSelectorList(ELEMENTS)
                .subList(
                    1,
                    4
                ), // 2, 3, 4
            1,
            3,
            new CurrencyExchangeRaterSelectorList(
                Lists.of(
                    CURRENCY_EXCHANGE_RATER_SELECTOR3,
                    CURRENCY_EXCHANGE_RATER_SELECTOR4
                )
            )
        );
    }

    @Override
    public CurrencyExchangeRaterSelectorList createList() {
        return CurrencyExchangeRaterSelectorList.EMPTY;
    }

    // HasCsvStringList.................................................................................................

    @Test
    public void testCsvStringListWithEmpty() {
        this.csvStringListAndCheck(
            CurrencyExchangeRaterSelectorList.EMPTY,
            ""
        );
    }

    @Test
    public void testCsvStringListWithNotEmpty() {
        this.csvStringListAndCheck(
            CurrencyExchangeRaterSelectorList.EMPTY.concat(CURRENCY_EXCHANGE_RATER_SELECTOR)
                .concat(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR),
            "\"test-currency-exchange-rater-111\"\n" +
                "\"test-different-currency-exchange-rater-222\""
        );
    }

    // HasTsvStringList.................................................................................................

    @Test
    public void testTsvStringListWithEmpty() {
        this.tsvStringListAndCheck(
            CurrencyExchangeRaterSelectorList.EMPTY,
            ""
        );
    }

    @Test
    public void testTsvStringListWithNotEmpty() {
        this.tsvStringListAndCheck(
            CurrencyExchangeRaterSelectorList.EMPTY.concat(CURRENCY_EXCHANGE_RATER_SELECTOR)
                .concat(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR),
            "\"test-currency-exchange-rater-111\"\n" +
                "\"test-different-currency-exchange-rater-222\""
        );
    }

    @Override
    public CurrencyExchangeRaterSelectorList createHasCsvStringListTsvStringList() {
        return this.createList();
    }

    // json.............................................................................................................

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            CurrencyExchangeRaterSelectorList.EMPTY.concat(CURRENCY_EXCHANGE_RATER_SELECTOR)
                .concat(DIFFERENT_CURRENCY_EXCHANGE_RATER_SELECTOR),
            "\"test-currency-exchange-rater-111,test-different-currency-exchange-rater-222\""
        );
    }

    @Override
    public CurrencyExchangeRaterSelectorList unmarshall(final JsonNode jsonNode,
                                                        final JsonNodeUnmarshallContext context) {
        return CurrencyExchangeRaterSelectorList.unmarshall(
            jsonNode,
            context
        );
    }

    @Override
    public CurrencyExchangeRaterSelectorList createJsonNodeMarshallingValue() {
        return this.createList();
    }
    
    // class............................................................................................................

    @Override
    public Class<CurrencyExchangeRaterSelectorList> type() {
        return CurrencyExchangeRaterSelectorList.class;
    }
}
