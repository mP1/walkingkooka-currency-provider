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

import walkingkooka.collect.set.ImmutableSortedSetDefaults;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.plugin.PluginAliasSet;
import walkingkooka.plugin.PluginAliasSetLike;
import walkingkooka.text.CharacterConstant;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;

public final class CurrencyExchangeRaterAliasSet extends AbstractSet<CurrencyExchangeRaterAlias>
    implements PluginAliasSetLike<CurrencyExchangeRaterName,
    CurrencyExchangeRaterInfo,
    CurrencyExchangeRaterInfoSet,
    CurrencyExchangeRaterSelector,
    CurrencyExchangeRaterAlias,
    CurrencyExchangeRaterAliasSet>,
    ImmutableSortedSetDefaults<CurrencyExchangeRaterAliasSet, CurrencyExchangeRaterAlias> {

    /**
     * An empty {@link CurrencyExchangeRaterAliasSet}.
     */
    public final static CurrencyExchangeRaterAliasSet EMPTY = new CurrencyExchangeRaterAliasSet(
        PluginAliasSet.with(
            SortedSets.empty(),
            CurrencyExchangeRaterPluginHelper.INSTANCE
        )
    );

    /**
     * {@see PluginAliasSet#SEPARATOR}
     */
    public final static CharacterConstant SEPARATOR = PluginAliasSet.SEPARATOR;

    public static CurrencyExchangeRaterAliasSet parse(final String text) {
        return new CurrencyExchangeRaterAliasSet(
            PluginAliasSet.parse(
                text,
                CurrencyExchangeRaterPluginHelper.INSTANCE
            )
        );
    }

    private CurrencyExchangeRaterAliasSet(final PluginAliasSet<CurrencyExchangeRaterName, CurrencyExchangeRaterInfo, CurrencyExchangeRaterInfoSet, CurrencyExchangeRaterSelector, CurrencyExchangeRaterAlias, CurrencyExchangeRaterAliasSet> pluginAliasSet) {
        this.pluginAliasSet = pluginAliasSet;
    }

    @Override
    public CurrencyExchangeRaterSelector selector(final CurrencyExchangeRaterSelector selector) {
        return this.pluginAliasSet.selector(selector);
    }

    @Override
    public Optional<CurrencyExchangeRaterSelector> aliasSelector(final CurrencyExchangeRaterName name) {
        return this.pluginAliasSet.aliasSelector(name);
    }

    @Override
    public Optional<CurrencyExchangeRaterName> aliasOrName(final CurrencyExchangeRaterName name) {
        return this.pluginAliasSet.aliasOrName(name);
    }

    @Override
    public CurrencyExchangeRaterInfoSet merge(final CurrencyExchangeRaterInfoSet infos) {
        return this.pluginAliasSet.merge(infos);
    }

    @Override
    public boolean containsAliasOrName(final CurrencyExchangeRaterName aliasOrName) {
        return this.pluginAliasSet.containsAliasOrName(aliasOrName);
    }

    @Override
    public CurrencyExchangeRaterAliasSet concatOrReplace(final CurrencyExchangeRaterAlias alias) {
        return new CurrencyExchangeRaterAliasSet(
            this.pluginAliasSet.concatOrReplace(alias)
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet deleteAliasOrNameAll(final Collection<CurrencyExchangeRaterName> aliasOrNames) {
        return this.setElements(
            this.pluginAliasSet.deleteAliasOrNameAll(aliasOrNames)
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet keepAliasOrNameAll(final Collection<CurrencyExchangeRaterName> aliasOrNames) {
        return this.setElements(
            this.pluginAliasSet.keepAliasOrNameAll(aliasOrNames)
        );
    }

    // ImmutableSortedSet...............................................................................................

    @Override
    public Comparator<? super CurrencyExchangeRaterAlias> comparator() {
        return this.pluginAliasSet.comparator();
    }

    @Override
    public Iterator<CurrencyExchangeRaterAlias> iterator() {
        return this.pluginAliasSet.stream().iterator();
    }

    @Override
    public int size() {
        return this.pluginAliasSet.size();
    }

    @Override
    public CurrencyExchangeRaterAliasSet setElements(final Collection<CurrencyExchangeRaterAlias> aliases) {
        final CurrencyExchangeRaterAliasSet currencyExchangeRaterAliasSet;

        // dont wrap if CurrencyExchangeRaterAliasSet
        if (aliases instanceof CurrencyExchangeRaterAliasSet) {
            currencyExchangeRaterAliasSet = (CurrencyExchangeRaterAliasSet) aliases;
        } else {
            final CurrencyExchangeRaterAliasSet after = new CurrencyExchangeRaterAliasSet(
                this.pluginAliasSet.setElements(aliases)
            );
            currencyExchangeRaterAliasSet = this.pluginAliasSet.equals(aliases) ?
                this :
                after;
        }

        return currencyExchangeRaterAliasSet;
    }

    @Override
    public CurrencyExchangeRaterAliasSet setElementsFailIfDifferent(final Collection<CurrencyExchangeRaterAlias> sortedSet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<CurrencyExchangeRaterAlias> toSet() {
        return this.pluginAliasSet.toSet();
    }

    @Override
    public CurrencyExchangeRaterAliasSet subSet(final CurrencyExchangeRaterAlias from,
                                                final CurrencyExchangeRaterAlias to) {
        return this.setElements(
            this.pluginAliasSet.subSet(
                from,
                to
            )
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet headSet(final CurrencyExchangeRaterAlias alias) {
        return this.setElements(
            this.pluginAliasSet.headSet(alias)
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet tailSet(final CurrencyExchangeRaterAlias alias) {
        return this.setElements(
            this.pluginAliasSet.tailSet(alias)
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet concat(final CurrencyExchangeRaterAlias alias) {
        return this.setElements(
            this.pluginAliasSet.concat(alias)
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet concatAll(final Collection<CurrencyExchangeRaterAlias> aliases) {
        return this.setElements(
            this.pluginAliasSet.concatAll(aliases)
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet delete(final CurrencyExchangeRaterAlias alias) {
        return this.setElements(
            this.pluginAliasSet.delete(alias)
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet deleteAll(final Collection<CurrencyExchangeRaterAlias> aliases) {
        return this.setElements(
            this.pluginAliasSet.deleteAll(aliases)
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet replace(final CurrencyExchangeRaterAlias oldAlias,
                                                 final CurrencyExchangeRaterAlias newAlias) {
        return this.setElements(
            this.pluginAliasSet.replace(
                oldAlias,
                newAlias
            )
        );
    }

    @Override
    public CurrencyExchangeRaterAlias first() {
        return this.pluginAliasSet.first();
    }

    @Override
    public CurrencyExchangeRaterAlias last() {
        return this.pluginAliasSet.last();
    }

    @Override
    public void elementCheck(final CurrencyExchangeRaterAlias alias) {
        Objects.requireNonNull(alias, "alias");
    }

    @Override
    public String text() {
        return this.pluginAliasSet.text();
    }

    @Override
    public void printTree(final IndentingPrinter printer) {
        this.pluginAliasSet.printTree(printer);
    }

    private final PluginAliasSet<CurrencyExchangeRaterName, CurrencyExchangeRaterInfo, CurrencyExchangeRaterInfoSet, CurrencyExchangeRaterSelector, CurrencyExchangeRaterAlias, CurrencyExchangeRaterAliasSet> pluginAliasSet;

    // Json.............................................................................................................

    static void register() {
        // helps force registry of json marshaller
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(
            this.pluginAliasSet.text()
        );
    }

    static CurrencyExchangeRaterAliasSet unmarshall(final JsonNode node,
                                                    final JsonNodeUnmarshallContext context) {
        return parse(
            node.stringOrFail()
        );
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(CurrencyExchangeRaterAliasSet.class),
            CurrencyExchangeRaterAliasSet::unmarshall,
            CurrencyExchangeRaterAliasSet::marshall,
            CurrencyExchangeRaterAliasSet.class
        );
        CurrencyExchangeRaterInfoSet.EMPTY.size(); // trigger static init and json marshall/unmarshall registry
    }
}