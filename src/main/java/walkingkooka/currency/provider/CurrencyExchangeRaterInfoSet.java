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

import walkingkooka.collect.set.Sets;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.plugin.PluginInfoSet;
import walkingkooka.plugin.PluginInfoSetLike;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

/**
 * A read only {@link Set} of {@link CurrencyExchangeRaterInfo} sorted by {@link CurrencyExchangeRaterName}.
 */
public final class CurrencyExchangeRaterInfoSet extends AbstractSet<CurrencyExchangeRaterInfo> implements PluginInfoSetLike<CurrencyExchangeRaterName, CurrencyExchangeRaterInfo, CurrencyExchangeRaterInfoSet, CurrencyExchangeRaterSelector, CurrencyExchangeRaterAlias, CurrencyExchangeRaterAliasSet> {

    public final static CurrencyExchangeRaterInfoSet EMPTY = new CurrencyExchangeRaterInfoSet(
        PluginInfoSet.with(
            Sets.<CurrencyExchangeRaterInfo>empty()
        )
    );

    public static CurrencyExchangeRaterInfoSet parse(final String text) {
        return new CurrencyExchangeRaterInfoSet(
            PluginInfoSet.parse(
                text,
                CurrencyExchangeRaterInfo::parse
            )
        );
    }

    private CurrencyExchangeRaterInfoSet(final PluginInfoSet<CurrencyExchangeRaterName, CurrencyExchangeRaterInfo> pluginInfoSet) {
        this.pluginInfoSet = pluginInfoSet;
    }

    // PluginInfoSetLike................................................................................................

    @Override
    public Set<CurrencyExchangeRaterName> names() {
        return this.pluginInfoSet.names();
    }

    @Override
    public Set<AbsoluteUrl> url() {
        return this.pluginInfoSet.url();
    }

    @Override
    public CurrencyExchangeRaterAliasSet aliasSet() {
        return CurrencyExchangeRaterPluginHelper.INSTANCE.toAliasSet(this);
    }

    @Override
    public CurrencyExchangeRaterInfoSet filter(final CurrencyExchangeRaterInfoSet infos) {
        return this.setElements(
            this.pluginInfoSet.filter(
                infos.pluginInfoSet
            )
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet renameIfPresent(CurrencyExchangeRaterInfoSet renameInfos) {
        return this.setElements(
            this.pluginInfoSet.renameIfPresent(
                renameInfos.pluginInfoSet
            )
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet concat(final CurrencyExchangeRaterInfo info) {
        return this.setElements(
            this.pluginInfoSet.concat(info)
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet concatAll(final Collection<CurrencyExchangeRaterInfo> infos) {
        return this.setElements(
            this.pluginInfoSet.concatAll(infos)
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet delete(final CurrencyExchangeRaterInfo info) {
        return this.setElements(
            this.pluginInfoSet.delete(info)
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet deleteAll(final Collection<CurrencyExchangeRaterInfo> infos) {
        return this.setElements(
            this.pluginInfoSet.deleteAll(infos)
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet deleteIf(final Predicate<? super CurrencyExchangeRaterInfo> predicate) {
        return this.setElements(
            this.pluginInfoSet.deleteIf(predicate)
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet replace(final CurrencyExchangeRaterInfo oldInfo,
                                                final CurrencyExchangeRaterInfo newInfo) {
        return this.setElements(
            this.pluginInfoSet.replace(
                oldInfo,
                newInfo
            )
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet setElementsFailIfDifferent(final Collection<CurrencyExchangeRaterInfo> infos) {
        return this.setElements(
            this.pluginInfoSet.setElementsFailIfDifferent(
                infos
            )
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet setElements(final Collection<CurrencyExchangeRaterInfo> infos) {
        CurrencyExchangeRaterInfoSet after;

        if (infos instanceof CurrencyExchangeRaterInfoSet) {
            after = (CurrencyExchangeRaterInfoSet) infos;
        } else {
            after = new CurrencyExchangeRaterInfoSet(
                this.pluginInfoSet.setElements(infos)
            );
            after = after.isEmpty() ?
                EMPTY :
                this.equals(after) ?
                    this :
                    after;
        }

        return after;
    }

    @Override
    public Set<CurrencyExchangeRaterInfo> toSet() {
        return this.pluginInfoSet.toSet();
    }

    // TreePrintable....................................................................................................

    @Override
    public String text() {
        return this.pluginInfoSet.text();
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());
        printer.indent();
        {
            this.pluginInfoSet.printTree(printer);
        }
        printer.outdent();
    }

    // AbstractSet......................................................................................................

    @Override
    public Iterator<CurrencyExchangeRaterInfo> iterator() {
        return this.pluginInfoSet.iterator();
    }

    @Override
    public int size() {
        return this.pluginInfoSet.size();
    }

    private final PluginInfoSet<CurrencyExchangeRaterName, CurrencyExchangeRaterInfo> pluginInfoSet;

    // json.............................................................................................................

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshallCollection(this);
    }

    // @VisibleForTesting
    static CurrencyExchangeRaterInfoSet unmarshall(final JsonNode node,
                                                   final JsonNodeUnmarshallContext context) {
        return EMPTY.setElements(
            context.unmarshallSet(
                node,
                CurrencyExchangeRaterInfo.class
            )
        );
    }

    static {
        CurrencyExchangeRaterInfo.register(); // force json registry

        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(CurrencyExchangeRaterInfoSet.class),
            CurrencyExchangeRaterInfoSet::unmarshall,
            CurrencyExchangeRaterInfoSet::marshall,
            CurrencyExchangeRaterInfoSet.class
        );
    }
}