/* This file is part of Minesweeper.
 * 
 * Minesweeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Minesweeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minesweeper.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.htwg.se.minesweeper;

import com.google.inject.AbstractModule;

import de.htwg.se.controller.IController;
import de.htwg.se.database.DataAccessObject;
import de.htwg.se.model.IField;


public class MinesweeperModule extends AbstractModule {

    @Override
    protected void configure() {
        
        bind(IController.class)
        .to(de.htwg.se.controller.impl.Controller.class);
        bind(IField.class)
        .to(de.htwg.se.model.impl.Field.class);
        bind(DataAccessObject.class).to(de.htwg.se.database.hibern8.HibernateDatabase.class);
        
    }

}
