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
import walkingkooka.collect.set.SortedSets;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.plugin.PluginNameTesting;
import walkingkooka.reflect.FieldAttributes;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.MethodAttributes;
import walkingkooka.text.CaseKind;
import walkingkooka.text.CharacterConstant;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

final public class CurrencyExchangeRaterNameTest implements PluginNameTesting<CurrencyExchangeRaterName> {

    @Test
    public void testConstantNamesMatchCurrencyExchangeRatersFactoryMethods() {
        final Set<String> constantNames = Arrays.stream(CurrencyExchangeRaterName.class.getFields())
            .filter(m -> FieldAttributes.STATIC.is(m))
            .filter(m -> JavaVisibility.of(m) == JavaVisibility.PUBLIC)
            .filter(m -> m.getType() == CurrencyExchangeRaterName.class)
            .map(m -> {
                try {
                    final CurrencyExchangeRaterName n = (CurrencyExchangeRaterName) m.get(null);
                    return n.value();
                } catch (final Exception rethrow) {
                    throw new Error(rethrow);
                }
            }).filter(n -> false == "fake".equals(n))
            .collect(Collectors.toCollection(SortedSets::tree));

        final Set<String> factoryNames = Arrays.stream(CurrencyExchangeRaters.class.getMethods())
            .filter(m -> MethodAttributes.STATIC.is(m))
            .filter(m -> JavaVisibility.of(m) == JavaVisibility.PUBLIC)
            .map(m -> m.getName())
            .filter(n -> false == "fake".equals(n))
            .map(m -> CaseKind.CAMEL.change(m, CaseKind.KEBAB).toLowerCase())
            .collect(Collectors.toCollection(SortedSets::tree));

        this.checkEquals(
            CharacterConstant.with('\n').toSeparatedString(
                constantNames,
                Function.identity()
            ),
            CharacterConstant.with('\n').toSeparatedString(
                factoryNames,
                Function.identity()
            )
        );
    }

    @Override
    public CurrencyExchangeRaterName createName(final String name) {
        return CurrencyExchangeRaterName.with(name);
    }

    @Override
    public Class<CurrencyExchangeRaterName> type() {
        return CurrencyExchangeRaterName.class;
    }

    @Override
    public CurrencyExchangeRaterName unmarshall(final JsonNode from,
                                                final JsonNodeUnmarshallContext context) {
        return CurrencyExchangeRaterName.unmarshall(from, context);
    }
}
